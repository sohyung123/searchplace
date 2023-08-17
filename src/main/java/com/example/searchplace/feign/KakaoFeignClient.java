package com.example.searchplace.feign;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${searchplace.feign.kakao.name}",url = "${searchplace.feign.kakao.url}",fallback = KakaoFeignClient.Fallback.class)
public interface KakaoFeignClient {

    @GetMapping(value = "v2/local/search/keyword.json",headers = "${searchplace.feign.kakao.header1.key}=${searchplace.feign.kakao.header1.value}")
    KakaoResponseDto getKakaoData(@SpringQueryMap KakaoParameterDto params);

    //Feign 실패시, Fallback으로 null값 리턴하여, kakao open api 장애시 naver open api 만이라도 사용하도록 설정
    @Component
    static class Fallback implements KakaoFeignClient {

        @Override
        public KakaoResponseDto getKakaoData(KakaoParameterDto params) {
            return null;
        }
    }

}
