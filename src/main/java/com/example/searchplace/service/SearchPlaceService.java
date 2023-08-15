package com.example.searchplace.service;

import com.example.searchplace.dto.*;
import com.example.searchplace.entity.Keyword;
import com.example.searchplace.repository.KeywordRepository;
import com.example.searchplace.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchPlaceService {

    private final KakaoDataService kakaoDataService;
    private final NaverDataService naverDataService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final KeywordRepository keywordRepository;

    public SearchResultDto keywordSearch(ParameterDto parameterDto) {
        SearchResultDto searchResultDto = new SearchResultDto();
        KakaoResponseDto kakaoResponseDto = kakaoDataService.getKakaoData(parameterDto);
        NaverResponseDto naverResponseDto = naverDataService.getNaverData(parameterDto);

        CopyOnWriteArrayList<String> kakaoData = kakaoResponseDto.getDocuments().stream()
                .map(i -> i.getPlaceName().replaceAll("<[^>]*>", ""))
                .map(j -> j.replace(" ", ""))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        CopyOnWriteArrayList<String> naverData = naverResponseDto.getItems().stream()
                .map(i -> i.getTitle().replaceAll("<[^>]*>", ""))
                .map(j -> j.replace(" ", ""))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        CopyOnWriteArrayList<String> commonData = kakaoData.stream()
                .filter(f -> naverData.stream().anyMatch(Predicate.isEqual(f)))
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        LinkedHashSet<String> set = new LinkedHashSet<>(commonData);
        set.addAll(kakaoData);
        set.addAll(naverData);
        CopyOnWriteArrayList<String> mergedList = new CopyOnWriteArrayList<>(set);
        searchResultDto.setPlaces(mergedList);

        applicationEventPublisher.publishEvent(parameterDto);

        return searchResultDto;
    }

    public KeywordListDto getKeywordList() {
        try{
            List<Keyword> keywordList = keywordRepository.findAll();
            CopyOnWriteArrayList<KeywordListDto.KeywordDto> resultList = keywordList.stream()
                    .sorted(Comparator.comparing(Keyword::getCount).reversed())
                    .limit(Constants.KEYWORDLISTSIZE)
                    .map(i->new KeywordListDto.KeywordDto(i.getKeywordName(),i.getCount()))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            KeywordListDto keywordListDto = new KeywordListDto();
            keywordListDto.setKeywords(resultList);
            return keywordListDto;
        }
        catch (RuntimeException e) {
            log.error("keyword List 조회 오류",e.getMessage(),e);
            return null;
        }
    }

}
