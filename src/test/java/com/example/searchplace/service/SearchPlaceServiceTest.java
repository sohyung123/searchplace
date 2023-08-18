package com.example.searchplace.service;

import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.dto.NaverResponseDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.dto.SearchResultDto;
import com.example.searchplace.repository.KeywordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchPlaceServiceTest {

    @InjectMocks
    private SearchPlaceService searchPlaceService;

    @Mock
    private KakaoDataService kakaoDataService;

    @Mock
    private NaverDataService naverDataService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @DisplayName("keywordSearch 메서드 테스트")
    @Test
    void keywordSearchTest() {
        //given
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto();
        ArrayList<KakaoResponseDto.KakaoDocumentDto> list = new ArrayList<>();
        KakaoResponseDto.KakaoDocumentDto kakaoDocumentDto1 = new KakaoResponseDto.KakaoDocumentDto();
        KakaoResponseDto.KakaoDocumentDto kakaoDocumentDto2 = new KakaoResponseDto.KakaoDocumentDto();
        KakaoResponseDto.KakaoDocumentDto kakaoDocumentDto3 = new KakaoResponseDto.KakaoDocumentDto();
        kakaoDocumentDto1.setPlaceName("롯데캐슬");
        kakaoDocumentDto2.setPlaceName("보라매공원");
        kakaoDocumentDto3.setPlaceName("신대방 역");
        list.add(kakaoDocumentDto1);
        list.add(kakaoDocumentDto2);
        list.add(kakaoDocumentDto3);

        kakaoResponseDto.setDocuments(list);
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setQuery("동작구");
        Mockito.doReturn(kakaoResponseDto).when(kakaoDataService).getKakaoData(parameterDto);

        NaverResponseDto naverResponseDto = new NaverResponseDto();
        ArrayList<NaverResponseDto.NaverItemDto> list2 = new ArrayList<>();
        NaverResponseDto.NaverItemDto naverItemDto1 = new NaverResponseDto.NaverItemDto();
        NaverResponseDto.NaverItemDto naverItemDto2 = new NaverResponseDto.NaverItemDto();
        NaverResponseDto.NaverItemDto naverItemDto3 = new NaverResponseDto.NaverItemDto();
        naverItemDto1.setTitle("보라매 공원");
        naverItemDto2.setTitle("신도림");
        naverItemDto3.setTitle("롯데 시네마");
        list2.add(naverItemDto1);
        list2.add(naverItemDto2);
        list2.add(naverItemDto3);

        naverResponseDto.setItems(list2);
        Mockito.doReturn(naverResponseDto).when(naverDataService).getNaverData(parameterDto);

        //when
        SearchResultDto searchResultDto = searchPlaceService.keywordSearch(parameterDto);

        //then
        Assertions.assertThat(searchResultDto.getPlaces().get(0)).isEqualTo("보라매공원");
        Assertions.assertThat(searchResultDto.getPlaces().get(1)).isEqualTo("롯데캐슬");
        Assertions.assertThat(searchResultDto.getPlaces().get(2)).isEqualTo("신대방역");
        Assertions.assertThat(searchResultDto.getPlaces().get(3)).isEqualTo("신도림");
        Assertions.assertThat(searchResultDto.getPlaces().get(4)).isEqualTo("롯데시네마");

    }
}