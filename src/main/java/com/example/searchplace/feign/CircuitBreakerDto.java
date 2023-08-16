package com.example.searchplace.feign;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.Data;

@Data
public class CircuitBreakerDto {

    private String name;
    private CircuitBreaker.State state;

    public CircuitBreakerDto(String name, CircuitBreaker.State state) {
        this.name = name;
        this.state = state;
    }
}
