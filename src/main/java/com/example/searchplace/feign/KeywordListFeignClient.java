package com.example.searchplace.feign;

import com.example.searchplace.dto.KeywordListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${searchplace.feign.keywordList.name}", url = "${searchplace.feign.keywordList.url}")
public interface KeywordListFeignClient {

    //keyword 서비스 port 8081로 띄운상태에서 실행해야 함.
    @GetMapping(value = "v2/keyword")
    KeywordListDto getKeywordList();
}
