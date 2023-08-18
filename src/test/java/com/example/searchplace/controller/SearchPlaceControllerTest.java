package com.example.searchplace.controller;

import com.example.searchplace.dto.KeywordListDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.dto.SearchResultDto;
import com.example.searchplace.service.KeywordListService;
import com.example.searchplace.service.SearchPlaceService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchPlaceControllerTest {

    @Autowired
    private SearchPlaceService searchPlaceService;

    @Autowired
    private KeywordListService keywordListService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @DisplayName("searchPlace 메서드 테스트")
    @Test
    void searchPlaceTest() {
        //given
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setQuery("동작구");

        //when
        SearchResultDto searchResultDto = searchPlaceService.keywordSearch(parameterDto);

        //then
        Assertions.assertThat(searchResultDto.getPlaces()).isNotNull();
    }

    @DisplayName("getKeywordList 메서드 테스트")
    @Test
    void getKeywordListTest() {
        //when
        KeywordListDto keywordListDto = keywordListService.getKeywordList();

        //then
        Assertions.assertThat(keywordListDto.getKeywords()).isNotNull();
        Assertions.assertThat(keywordListDto.getKeywords().get(0).getKeyword()).isEqualTo("치킨");

    }
}