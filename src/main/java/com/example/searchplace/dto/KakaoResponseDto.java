package com.example.searchplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class KakaoResponseDto {

    private List<KakaoDocumentDto> documents;
    private KakaoMetaDto meta;

}
