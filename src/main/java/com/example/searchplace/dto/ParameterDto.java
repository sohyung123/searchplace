package com.example.searchplace.dto;

import com.example.searchplace.entity.Keyword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParameterDto {

    private String query;
//    public static final int SIZE = 5;

    public static Keyword toEntity(ParameterDto parameterDto) {
        return Keyword.builder()
                .keywordName(parameterDto.getQuery())
                .count(1)
                .crtTm(null)
                .chgTm(null)
                .build();
    }

}
