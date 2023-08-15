package com.example.searchplace.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverParameterDto {

    private String query;

    private Integer display;

}
