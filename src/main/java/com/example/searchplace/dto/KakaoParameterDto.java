package com.example.searchplace.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoParameterDto {

    private String query;
    private Integer size;

}
