package com.example.searchplace.controller;

import com.example.searchplace.dto.*;
import com.example.searchplace.service.SearchPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class SearchPlaceController {

    private final SearchPlaceService searchPlaceService;

    @GetMapping("/place")
    public SearchResultDto searchPlace(@ModelAttribute ParameterDto parameterDto) {
        SearchResultDto searchResultDto = searchPlaceService.keywordSearch(parameterDto);
        return searchResultDto;
    }

    @GetMapping("keyword")
    public KeywordListDto getKeywordList() {
        KeywordListDto keywordListDto = searchPlaceService.getKeywordList();
        return keywordListDto;
    }

}
