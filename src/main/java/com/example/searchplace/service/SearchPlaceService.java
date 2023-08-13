package com.example.searchplace.service;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.feign.KakaoFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchPlaceService {

    private final KakaoFeignClient kakaoFeignClient;

    public KakaoResponseDto getKakaoData(@ModelAttribute KakaoParameterDto kakaoParameterDto) {
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();
        log.info("query : {}, size : {}",kakaoParameterDto.getQuery(),kakaoParameterDto.getSize());
        try {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            StringBuffer requestURL = servletRequest.getRequestURL();
            log.info("requestURL : {}",requestURL.toString());
            kakaoResponseDto = kakaoFeignClient.getKakaoData(kakaoParameterDto);
        }
        catch (Exception e) {
            log.error("get kakao Data Failed.", e.getMessage(),e);
        }
        return  kakaoResponseDto;
    }


}
