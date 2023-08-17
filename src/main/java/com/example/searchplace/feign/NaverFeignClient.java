package com.example.searchplace.feign;

import com.example.searchplace.dto.NaverParameterDto;
import com.example.searchplace.dto.NaverResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${searchplace.feign.naver.name}",url = "${searchplace.feign.naver.url}",fallback = NaverFeignClient.Fallback.class)
public interface NaverFeignClient {

    @GetMapping(value = "v1/search/local.json",
            headers = {"${searchplace.feign.naver.header1.key}=${searchplace.feign.naver.header1.value}"
                    ,"${searchplace.feign.naver.header2.key}=${searchplace.feign.naver.header2.value}"})
    NaverResponseDto getNaverData(@SpringQueryMap NaverParameterDto params);

    //Feign 실패시, Fallback으로 null값 리턴하여, naver open api 장애시 kakao open api 만이라도 사용하도록 설정
    @Component
    static class Fallback implements NaverFeignClient {

        @Override
        public NaverResponseDto getNaverData(NaverParameterDto params) {
            return null;
        }
    }

}
