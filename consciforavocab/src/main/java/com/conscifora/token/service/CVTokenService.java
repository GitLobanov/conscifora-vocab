package com.conscifora.token.service;

import com.conscifora.token.repository.CVTokenRepository;
import com.conscifora.token.domain.CVToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@Service
@Slf4j
public class CVTokenService {

    private CVTokenRepository cVTokenRepository;

    public String createToken() {
        String tokenValue = UUID.randomUUID().toString();

        CVToken token = new CVToken();
        token.setTokenValue(tokenValue);
        token.setCreatedAt(Instant.now().getEpochSecond());

        // TODO change logic getting expires time
        token.setExpiresIn(Instant.now().plusSeconds(365 * 24 * 60 * 60).getEpochSecond());

        cVTokenRepository.saveToken(tokenValue, token);

        return tokenValue;
    }

    public boolean validateToken(String value) {
        CVToken token = cVTokenRepository.getTokenByValue(value);
        if (token == null) {
            System.out.println("Токен не найден или срок действия истек.");
            return false;
        }

        long currentTime = Instant.now().getEpochSecond();

        if (currentTime > token.getExpiresIn()) {
            System.out.println("Срок действия токена истек.");
            return false;
        }

        return true;
    }

    public void incrementRequestCount(String value, String type) {
        cVTokenRepository.incrementRequestCount(value, type);
    }

    public long getRequestCount(String value, String type) {
        return cVTokenRepository.getRequestCount(value, type);
    }

    public String handleRequest(String value) {
        if (!validateToken(value)) {
            return "Токен недействителен или истек срок действия.";
        }

        // Получаем количество запросов
        long monthlyRequests = getRequestCount(value, "monthly");
        long totalRequests = getRequestCount(value, "total");

        if (monthlyRequests >= 100) {
            return "Месячный лимит запросов исчерпан.";
        }

        if (totalRequests >= 10000) {
            return "Общий лимит запросов исчерпан.";
        }

        incrementRequestCount(value, "monthly");
        incrementRequestCount(value, "total");

        return "Запрос успешно выполнен!";
    }

    public boolean removeToken (String token) {
        CVToken cvToken = cVTokenRepository.getTokenByValue(token);

        if (cvToken == null) return false;

        return cVTokenRepository.removeKeysWithToken(cvToken.getTokenValue())
                && cVTokenRepository.removeTokenByUser(cvToken.getUserId(), cvToken.getTokenValue());
    }

    public CVToken getToken(String token) {
        return cVTokenRepository.getTokenByValue(token);
    }

}
