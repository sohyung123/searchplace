package com.example.searchplace.service;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.feign.KakaoFeignClient;
import com.example.searchplace.utils.Constants;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoDataService {

    private final KakaoFeignClient kakaoFeignClient;

    public KakaoResponseDto getKakaoData(ParameterDto parameterDto) {
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();
        try {
            KakaoParameterDto params = KakaoParameterDto.builder()
                    .query(parameterDto.getQuery())
                    .size(Constants.SEARCHPLACESIZE)
                    .build();
            kakaoResponseDto = kakaoFeignClient.getKakaoData(params);
        }
        catch (FeignException e) {
            log.error("get kakao Data Failed.", e.getMessage(),e);
        }
        return kakaoResponseDto;
    }
}
