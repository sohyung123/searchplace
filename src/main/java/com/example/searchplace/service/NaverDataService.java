package com.example.searchplace.service;

import com.example.searchplace.dto.NaverParameterDto;
import com.example.searchplace.dto.NaverResponseDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.feign.NaverFeignClient;
import com.example.searchplace.utils.Constants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverDataService {

    private final NaverFeignClient naverFeignClient;

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
        }
        return naverResponseDto;
    }
}
