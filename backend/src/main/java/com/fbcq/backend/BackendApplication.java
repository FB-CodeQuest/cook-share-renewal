package com.fbcq.backend;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("backend").load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        // 환경 변수 로그 출력
        log.info("[환경 변수 확인]");
//        log.info("DB_URL      = {}", System.getProperty("DB_URL"));
        log.info("DB_USERNAME = {}", System.getProperty("DB_USERNAME"));
//        log.info("DB_PASSWORD = {}", System.getProperty("DB_PASSWORD")); // 운영 시 노출 주의

        SpringApplication.run(BackendApplication.class, args);
    }

}
