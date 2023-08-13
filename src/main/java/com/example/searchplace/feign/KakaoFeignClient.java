package com.example.searchplace.feign;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@FeignClient(value = "kakao",url = "https://dapi.kakao.com")
public interface KakaoFeignClient {

    @GetMapping(value = "v2/local/search/keyword.json",headers = "Authorization=KakaoAK 51fd6e1b5fb862ffa1f71098737702e4")
    KakaoResponseDto getKakaoData(@ModelAttribute KakaoParameterDto kakaoParameterDto);

}
