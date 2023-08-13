package com.example.searchplace.dto;

import lombok.Data;

@Data
public class KakaoMetaDto {

    //현재 페이지가 마지막 페이지인지 여부
    //값이 false면 다음 요청 시 page 값을 증가시켜 다음 페이지 요청 가능
    private boolean isEnd;

    //	total_count 중 노출 가능 문서 수 (최대: 45)
    private Integer pageableCount;

    //질의어의 지역 및 키워드 분석 정보
    private KakaoSameNameDto sameName;

    //검색어에 검색된 문서 수
    private Integer totalCount;
}
