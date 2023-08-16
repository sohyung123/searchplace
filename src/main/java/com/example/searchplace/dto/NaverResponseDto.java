package com.example.searchplace.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NaverResponseDto {

    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverItemDto> items;

    @Data
    public static class NaverItemDto {

        private String title;
        private String link;
        private String category;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private Integer mapx;
        private Integer mapy;

    }
}
