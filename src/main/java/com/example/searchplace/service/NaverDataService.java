package com.example.searchplace.service;

import com.example.searchplace.dto.NaverParameterDto;
import com.example.searchplace.dto.NaverResponseDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.feign.NaverFeignClient;
import com.example.searchplace.utils.Constants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverDataService {

    private final NaverFeignClient naverFeignClient;

    //naver open api 결과값은 Cache 처리하여, 반응성 향상
    @Cacheable(value = "naverCache")
    public NaverResponseDto getNaverData(ParameterDto parameterDto) {
        NaverResponseDto naverResponseDto = new NaverResponseDto();
        try {
            NaverParameterDto params = NaverParameterDto.builder()
                    .query(parameterDto.getQuery())
                    .display(Constants.SEARCHPLACESIZE)
                    .build();
            naverResponseDto = naverFeignClient.getNaverData(params);
        }
        catch (FeignException e) {
            log.error("get naver Data Failed.", e.getMessage(),e);
            naverResponseDto = null;
        }
        return naverResponseDto;
    }
}
