package com.example.searchplace.dto;

import lombok.Data;

@Data
public class KakaoDocumentDto {

    //전체 지번 주소
    private String addressName;

    //중요 카테고리만 그룹핑한 카테고리 그룹 코드
    private String categoryGroupCode;

    //중요 카테고리만 그룹핑한 카테고리 그룹명
    private String categoryGroupName;

    //카테고리 이름
    private String categoryName;

    //중심좌표까지의 거리 (단, x,y 파라미터를 준 경우에만 존재)
    //단위 meter
    private String distance;

    //장소 ID
    private String id;

    //전화번호
    private String phone;

    //장소명, 업체명
    private String placeName;

    //장소 상세페이지 URL
    private String placeUrl;

    //전체 도로명 주소
    private String roadAddressName;

    //X 좌표값, 경위도인 경우 longitude (경도)
    private String x;

    //Y 좌표값, 경위도인 경우 latitude(위도)
    private String y;

}
