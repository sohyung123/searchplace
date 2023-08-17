package com.example.searchplace.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverParameterDto {

    private String query;

    private Integer display;

}
