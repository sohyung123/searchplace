package com.example.searchplace.config;

import feign.Retryer;
import feign.Target;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        // 1초 간격으로 시작, 3초씩 점차 증가하여, 최대5번 재시도.
        return new Retryer.Default(1000L, TimeUnit.SECONDS.toMillis(3L), 5);
    }

    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (String feignClientName, Target<?> target, Method method) -> feignClientName + "_" + method.getName();
    }

}
