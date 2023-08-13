package com.example.searchplace.controller;

import com.example.searchplace.dto.KakaoParameterDto;
import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.service.SearchPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class KakaoTestController {

    private final SearchPlaceService searchPlaceService;

    @GetMapping("place")
    public KakaoResponseDto getKakaoData(@ModelAttribute KakaoParameterDto kakaoParameterDto) {
        KakaoResponseDto kakaoResponseDto = searchPlaceService.getKakaoData(kakaoParameterDto);
        return kakaoResponseDto;
    }

}
