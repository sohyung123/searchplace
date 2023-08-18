package com.example.searchplace.config;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.feign.KakaoFeignClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FeignConfigTest {

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    KakaoFeignClient kakaoFeignClient;

    @DisplayName("feign circuit 설정 테스트")
    @Test
    void circuitBreakerTest() {
        //given
        String circuitName = "kakao_getKakaoData";
        KakaoParameterDto tempDto = KakaoParameterDto.builder().size(5).query("잠실").build();

        //when
        circuitBreakerRegistry.circuitBreaker(circuitName).transitionToOpenState();
        CircuitBreaker.State state1 = circuitBreakerRegistry.circuitBreaker(circuitName).getState();

        //then
        Assertions.assertThat(state1).isEqualTo(CircuitBreaker.State.OPEN);

        //when
        KakaoResponseDto kakaoData = kakaoFeignClient.getKakaoData(tempDto);
        //then
        Assertions.assertThat(kakaoData).isNull();

        //when
        circuitBreakerRegistry.circuitBreaker(circuitName).transitionToClosedState();
        CircuitBreaker.State state2 = circuitBreakerRegistry.circuitBreaker(circuitName).getState();
        //then
        Assertions.assertThat(state2).isEqualTo(CircuitBreaker.State.CLOSED);

        //when
        KakaoResponseDto kakaoData2 = kakaoFeignClient.getKakaoData(tempDto);
        //then
        Assertions.assertThat(kakaoData2).isNotNull();

    }

}