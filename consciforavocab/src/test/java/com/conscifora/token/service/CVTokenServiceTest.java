package com.conscifora.token.service;


import com.conscifora.token.config.RedisCVTokenConfig;
import com.conscifora.token.domain.CVToken;
import com.conscifora.vocab.ConsciforavocabApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = {RedisCVTokenConfig.class, ConsciforavocabApplication.class})
@Slf4j
class CVTokenServiceTest {

    @Autowired
    CVTokenService cVTokenService;

    @Test
    public void testRedisStorageLogic_saveAndReadData() {
        String tokenValue = cVTokenService.createToken();
        log.info("Created token: {}", tokenValue);

        String res = cVTokenService.handleRequest(tokenValue);
        log.info("res: {}", res);

        long monthly = cVTokenService.getRequestCount(tokenValue, "monthly");
        long total = cVTokenService.getRequestCount(tokenValue, "total");

        log.info("monthly: {}", monthly);
        log.info("total: {}", total);
    }

    @Test
    public void testRedisStorageLogic_deleteToken() {
        String tokenValue = "88bf315a-f5ec-41aa-93b4-974dae4073f1";
        boolean isRemoved = cVTokenService.removeToken(tokenValue);
        assert (isRemoved);
        log.info("Removed token: {}", tokenValue);
    }


    @Test
    public void testRedisStorageLogic_searchToken() {
        String tokenValue = "88bf315a-f5ec-41aa-93b4-974dae4073f1";
        CVToken cvToken = cVTokenService.getToken(tokenValue);
        log.info("Got token: {}", cvToken);
    }

}