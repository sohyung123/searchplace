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

@Service
@Slf4j
@RequiredArgsConstructor
public class SaveKeywordService {

    private final KeywordRepository keywordRepository;
    @Qualifier("eventListenerThreadExecutor")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

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

    private Keyword getTargetKeyword(ParameterDto parameterDto) {
        String keyword = parameterDto.getQuery();
        try {
            Keyword existKeyword = keywordRepository.findByKeywordName(keyword);
            Keyword target = Keyword.builder()
                    .id(existKeyword.getId())
                    .keywordName(keyword)
                    .count(existKeyword.getCount().intValue() + 1)
                    .build();
            return target;
        }
        catch (RuntimeException e) {
            log.info("{}라는 키워드로는 검색된적 없습니다.",keyword);
            Keyword target = Keyword.builder()
                    .keywordName(keyword)
                    .count(1)
                    .build();
            return target;
        }
    }
}
