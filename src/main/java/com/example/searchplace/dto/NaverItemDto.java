package com.example.searchplace.dto;

import lombok.Data;

@Data
public class NaverItemDto {

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
