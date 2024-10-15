package com.conscifora.token.config;

import com.conscifora.token.domain.CVToken;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.*;

import java.util.Set;

@Configuration
public class RedisCVTokenConfig {

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setDatabase(0);
        return configuration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    private <T> RedisTemplate<String, T> createTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<T> valueSerializer) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer);
        return template;
    }

    @Bean
    public RedisTemplate<String, CVToken> redisCVTokenTemplate(RedisConnectionFactory redisConnectionFactory) {
        return createTemplate(redisConnectionFactory, new Jackson2JsonRedisSerializer<>(CVToken.class));
    }

    @Bean
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory redisConnectionFactory) {
        return createTemplate(redisConnectionFactory, new GenericJackson2JsonRedisSerializer());
    }

    @Bean
    public RedisTemplate<String, Long> redisLongTemplate(RedisConnectionFactory redisConnectionFactory) {
        return createTemplate(redisConnectionFactory, new GenericToStringSerializer<>(Long.class));
    }

    @Bean
    public RedisTemplate<String, Set<String>> redisSetsStringsTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Set<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }



}
