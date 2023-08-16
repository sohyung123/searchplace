package com.example.searchplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KakaoResponseDto {

    private List<KakaoDocumentDto> documents;
    private KakaoMetaDto meta;

    @Data
    public static class KakaoDocumentDto {
        @JsonProperty(value="address_name")
        private String addressName;
        @JsonProperty(value="category_group_code")
        private String categoryGroupCode;
        @JsonProperty(value="category_group_name")
        private String categoryGroupName;
        @JsonProperty(value="category_name")
        private String categoryName;
        private String distance;
        private String id;
        private String phone;
        @JsonProperty(value="place_name")
        private String placeName;
        @JsonProperty(value="place_url")
        private String placeUrl;
        @JsonProperty(value="road_address_name")
        private String roadAddressName;
        private String x;
        private String y;
    }

    @Data
    public static class KakaoMetaDto {
        @JsonProperty(value="is_end")
        private boolean isEnd;
        @JsonProperty(value="pageable_count")
        private Integer pageableCount;
        @JsonProperty(value="same_name")
        private KakaoSameNameDto sameName;
        @JsonProperty(value="total_count")
        private Integer totalCount;
    }

    @Data
    public static class KakaoSameNameDto {
        private String keyword;
        private String[] region;
        @JsonProperty(value="selected_region")
        private String selectedRegion;
    }
}
