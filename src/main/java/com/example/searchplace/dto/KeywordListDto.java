package com.example.searchplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class KeywordListDto {

    List<KeywordDto> keywords;

    @Data
    @AllArgsConstructor
    public static class KeywordDto {
        private String keyword;
        private Integer count;
    }

}
