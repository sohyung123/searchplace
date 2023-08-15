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

}
