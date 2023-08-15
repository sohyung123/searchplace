package com.example.searchplace.feign;

import com.example.searchplace.config.FeignRetryConfig;
import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.dto.NaverParameterDto;
import com.example.searchplace.dto.NaverResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "naver",url = "https://openapi.naver.com")
public interface NaverFeignClient {

    @GetMapping(value = "v1/search/local.json",headers = {"X-Naver-Client-Id=CwNB3MspiqwJBWYMuUYT","X-Naver-Client-Secret=iuGJFBep9H"})
    NaverResponseDto getNaverData(@SpringQueryMap NaverParameterDto params);

}
