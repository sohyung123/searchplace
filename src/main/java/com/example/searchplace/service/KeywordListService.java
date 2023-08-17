package com.example.searchplace.service;

import com.example.searchplace.dto.KeywordListDto;
import com.example.searchplace.feign.KeywordListFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeywordListService {

    private final KeywordListFeignClient keywordListFeignClient;

    //keyword 서비스 port 8081로 띄운상태에서 실행해야 함.
    public KeywordListDto getKeywordList() {
        try {
            KeywordListDto keywordListDto = keywordListFeignClient.getKeywordList();
            return keywordListDto;
        }
        catch (FeignException e) {
            log.error("get KeywordList Failed.",e.getMessage(),e);
            return null;
        }
    }

}
