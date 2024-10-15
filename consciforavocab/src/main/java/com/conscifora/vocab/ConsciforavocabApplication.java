package com.conscifora.vocab;

import com.conscifora.token.service.CVTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.conscifora")
@Slf4j
public class ConsciforavocabApplication {


    private static CVTokenService cvTokenService;

    public ConsciforavocabApplication(CVTokenService cvTokenService) {
        ConsciforavocabApplication.cvTokenService = cvTokenService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsciforavocabApplication.class, args);
    }

}
