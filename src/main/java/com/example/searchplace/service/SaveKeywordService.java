package com.example.searchplace.service;

import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.entity.Keyword;
import com.example.searchplace.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveKeywordService {

    private final KeywordRepository keywordRepository;
    @Qualifier("eventListenerThreadExecutor")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    //SearchPlaceService에서 전달한 event를 받아 비동기로 실행.
    @Async("eventListenerThreadExecutor")
    @EventListener
    public void saveKeywordData(ParameterDto parameterDto) {
        Keyword target = getTargetKeyword(parameterDto);
        try{
            keywordRepository.save(target);
        }
        catch (RuntimeException e) {
            log.error("keyword 저장 오류 발생",e.getMessage(),e);
        }
    }

    //특정 키워드가 호출된적 있는지 확인하는 메서드로, 호출될때마다 AtomicInteger인 newcount를 incrementAndGet()하여 1씩 증가하여 db에 저장
    private Keyword getTargetKeyword(ParameterDto parameterDto) {
        String keyword = parameterDto.getQuery();
        try {
            Keyword existKeyword = keywordRepository.findByKeywordName(keyword);
            AtomicInteger newcount = new AtomicInteger(existKeyword.getCount().intValue());
            Keyword target = Keyword.builder()
                    .id(existKeyword.getId())
                    .keywordName(keyword)
                    .count(newcount.incrementAndGet())
                    .build();
            return target;
        }
        catch (RuntimeException e) {
            log.info("{}(이)라는 키워드로는 검색된적 없습니다.",keyword);
            Keyword target = Keyword.builder()
                    .keywordName(keyword)
                    .count(1)
                    .build();
            return target;
        }
    }
}
