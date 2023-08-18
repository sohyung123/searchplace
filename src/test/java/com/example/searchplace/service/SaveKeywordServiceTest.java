package com.example.searchplace.service;

import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.entity.Keyword;
import com.example.searchplace.repository.KeywordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveKeywordServiceTest {

    @InjectMocks
    private SaveKeywordService saveKeywordService;

    @Mock
    private KeywordRepository keywordRepository;

    @DisplayName("getTargetKeyword 메서드 테스트")
    @Test
    void getTargetKeywordTest() {
        //given
        String place = "동작구";
        Keyword keyword = Keyword.builder().count(100).keywordName(place).build();
        Mockito.doReturn(keyword).when(keywordRepository).findByKeywordName(place);

        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setQuery(place);

        //when
        Keyword targetKeyword = saveKeywordService.getTargetKeyword(parameterDto);

        //then
        Assertions.assertThat(targetKeyword.getKeywordName()).isEqualTo(place);
        Assertions.assertThat(targetKeyword.getCount()).isEqualTo(101);

        //given
        ParameterDto parameterDto2 = new ParameterDto();
        parameterDto2.setQuery("잠실");

        //when
        Keyword targetKeyword2 = saveKeywordService.getTargetKeyword(parameterDto2);

        //then
        Assertions.assertThat(targetKeyword2.getKeywordName()).isEqualTo("잠실");
        Assertions.assertThat(targetKeyword2.getCount()).isEqualTo(1);

    }
}