package com.example.searchplace.config;

import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Configuration
public class FeignRetryConfig {

    @Bean
    public Retryer retryer() {
        // 0.1초의 간격으로 시작해 3초의 간격으로 점점 증가하며, 최대5번 재시도한다.
        return new Retryer.Default(1000L, TimeUnit.SECONDS.toMillis(3L), 5);
    }

    @Bean
    public ErrorDecoder decoder() {
        return (methodKey, response) -> {
            if(HttpStatus.valueOf(response.status()).is5xxServerError()) {
                return new RetryableException(response.status(), format("%s 요청이 성공하지 못했습니다. Retry 합니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()),
                        response.request().httpMethod(), Date.from(Instant.now()), response.request());
            }
            else {
                return new IllegalStateException(format("%s 요청이 성공하지 못했습니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()));
            }
        };
    }

}
