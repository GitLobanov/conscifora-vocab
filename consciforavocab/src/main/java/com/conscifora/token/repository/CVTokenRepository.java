package com.conscifora.token.repository;

import com.conscifora.token.domain.CVToken;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
@AllArgsConstructor
public class CVTokenRepository {

    private RedisTemplate<String, Object> redisObjectTemplate;
    private RedisTemplate<String, CVToken> redisCVTokenTemplate;
    private RedisTemplate<String, Long> redisLongTemplate;
    private RedisTemplate<String, Set<String>> redisSetsStringsTemplate;

    public void saveToken(String idUser, CVToken token) {
        redisCVTokenTemplate.opsForValue().set("cvtoken:%s".formatted(token.getTokenValue()), token);
        redisObjectTemplate.opsForSet().add("cvtoken:idUser:%s".formatted(idUser), token.getTokenValue());
    }

    public Set<String> getTokensByIdUser(String userId) {
        String tokenKey = "cvtoken:%s".formatted(userId);
        return redisSetsStringsTemplate.opsForValue().get(tokenKey);
    }

    public CVToken getTokenByValue(String tokenValue) {
        return redisCVTokenTemplate.opsForValue().get("cvtoken:%s".formatted(tokenValue));
    }

    public void incrementRequestCount(String userId, String type) {
        String countKey = "cvtoken:%s:%s_requests".formatted(userId, type);
        redisLongTemplate.opsForValue().increment(countKey, 1);
    }

    // TODO Have to know how this work in redis
    public Long getRequestCount(String userId, String type) {
        String countKey = "cvtoken:%s:%s_requests".formatted(userId, type);
        Long count = redisLongTemplate.opsForValue().get(countKey);
        return count != null ? count : 0L;
    }

    public boolean removeKeysWithToken(String token) {
        String pattern = "*%s*".formatted(token);

        Set<String> keys = redisObjectTemplate.keys(pattern);

        boolean isRemoved = false;
        if (keys != null && !keys.isEmpty()) {
            redisObjectTemplate.delete(keys);
            isRemoved = true;
        }

        return isRemoved;
    }

    public boolean removeTokenByUser(String userId, String tokenValue) {
        String userKey = "cvtoken:idUser:%s".formatted(userId);

        Long isTokenRemoved = redisObjectTemplate.opsForSet().remove(userKey, tokenValue);

        boolean isRemoved = false;
        if (isTokenRemoved != null && isTokenRemoved >= 0) {
            Long remainingTokens = redisObjectTemplate.opsForSet().size(userKey);
            if (remainingTokens != null && remainingTokens == 0) {
                redisObjectTemplate.delete(userKey);
            }
            isRemoved = true;
        }

        return isRemoved;
    }

}
