package com.example.searchplace.service;

import com.example.searchplace.dto.KakaoResponseDto;
import com.example.searchplace.dto.NaverResponseDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
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

    //1) 장소 검색 api에서 사용하는 메서드로, kakao 혹은 naver open api 장애시, 나머지 결과값으로라도 return하도록 if문 구성
    //공통 장소로 판단하는 기준은 태그제거, 공백제거하여 같으면 commonData로 판단.
    public SearchResultDto keywordSearch(ParameterDto parameterDto) {
        SearchResultDto searchResultDto = new SearchResultDto();
        KakaoResponseDto kakaoResponseDto = kakaoDataService.getKakaoData(parameterDto);
        NaverResponseDto naverResponseDto = naverDataService.getNaverData(parameterDto);

        if(kakaoResponseDto != null && naverResponseDto != null) {
            CopyOnWriteArrayList<String> kakaoData = kakaoResponseDto.getDocuments().stream()
                    .map(i -> i.getPlaceName().replaceAll("<[^>]*>", ""))   //name에 붙은 태그 제거
                    .map(j -> j.replace(" ", ""))   //name에 공백 제거
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            CopyOnWriteArrayList<String> naverData = naverResponseDto.getItems().stream()
                    .map(i -> i.getTitle().replaceAll("<[^>]*>", ""))   //name에 붙은 태그 제거
                    .map(j -> j.replace(" ", ""))   //name에 공백 제거
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            CopyOnWriteArrayList<String> commonData = kakaoData.stream()
                    .filter(f -> naverData.stream().anyMatch(Predicate.isEqual(f))) //kakao와 naver 공통 장소 추출
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            LinkedHashSet<String> set = new LinkedHashSet<>(commonData);
            set.addAll(kakaoData);
            set.addAll(naverData);
            CopyOnWriteArrayList<String> mergedList = new CopyOnWriteArrayList<>(set);
            searchResultDto.setPlaces(mergedList);
        }
        else if(kakaoResponseDto != null && naverResponseDto == null) {
            CopyOnWriteArrayList<String> kakaoData = kakaoResponseDto.getDocuments().stream()
                    .map(i -> i.getPlaceName().replaceAll("<[^>]*>", ""))   //name에 붙은 태그 제거
                    .map(j -> j.replace(" ", ""))   //name에 공백 제거
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
            searchResultDto.setPlaces(kakaoData);
        }
        else if(kakaoResponseDto == null && naverResponseDto != null) {
            CopyOnWriteArrayList<String> naverData = naverResponseDto.getItems().stream()
                    .map(i -> i.getTitle().replaceAll("<[^>]*>", ""))   //name에 붙은 태그 제거
                    .map(j -> j.replace(" ", ""))   //name에 공백 제거
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
            searchResultDto.setPlaces(naverData);
        }

        //장소 검색 메서드(keywordSearch) 핵심 business logic이라 판단.
        //혹시 모를 DB장애로 인해 keyword 테이블에 count를 증가시키는 Logic이 실패하더라도, 장소 검색 결과값을 return할수 있도록 ApplicationEventPublisher로 event 보내는 방식으로 구현
        applicationEventPublisher.publishEvent(parameterDto);

        return searchResultDto;
    }

    //Feign 관련 캐시를 1시간마다 스케쥴로 삭제.
    @Scheduled(cron = "0 0 0/1 * * *") //1시간마다 캐시 삭제
    @Caching(evict = {
            @CacheEvict(value = "kakaoCache",allEntries = true),
            @CacheEvict(value = "naverCache",allEntries = true)
    })
    public void deleteCache() {
        log.debug("Feign 관련 Cache 삭제");
    }

}
