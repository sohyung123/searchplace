package com.example.searchplace.controller;

import com.example.searchplace.dto.KeywordListDto;
import com.example.searchplace.dto.ParameterDto;
import com.example.searchplace.dto.SearchResultDto;
import com.example.searchplace.feign.CircuitBreakerDto;
import com.example.searchplace.service.SearchPlaceService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@Slf4j
public class SearchPlaceController {

    private final SearchPlaceService searchPlaceService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;


    @GetMapping("/place")
    public SearchResultDto searchPlace(@ModelAttribute ParameterDto parameterDto) {
        SearchResultDto searchResultDto = searchPlaceService.keywordSearch(parameterDto);
        return searchResultDto;
    }

    @GetMapping("keyword")
    public KeywordListDto getKeywordList() {
        KeywordListDto keywordListDto = searchPlaceService.getKeywordList();
        return keywordListDto;
    }

    @GetMapping("/circuit/close")
    public void closeCircuit(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToClosedState();
        String circuitName = circuitBreakerRegistry.circuitBreaker(name).getName();
        log.info("{} : is closed",circuitName);
    }

    @GetMapping("/circuit/open")
    public void openCircuit(@RequestParam String name) {
        circuitBreakerRegistry.circuitBreaker(name).transitionToOpenState();
        String circuitName = circuitBreakerRegistry.circuitBreaker(name).getName();
        log.info("{} : is opened",circuitName);
    }

    @GetMapping("/circuit/status")
    public CircuitBreaker.State CircuitStatus(@RequestParam String name) {
        CircuitBreaker.State state = circuitBreakerRegistry.circuitBreaker(name).getState();
        return state;
    }

    @GetMapping("/circuit/all")
    public ArrayList<CircuitBreakerDto> allCircuit() {
        Set<CircuitBreaker> circuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers();
        ArrayList<CircuitBreakerDto> list = new ArrayList<>();
        for (CircuitBreaker circuitBreaker : circuitBreakers) {
            CircuitBreakerDto circuitBreakerDto = new CircuitBreakerDto(circuitBreaker.getName(),circuitBreaker.getState());
            list.add(circuitBreakerDto);
        }
        return list;
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException e) {
        return ResponseEntity.badRequest()
                .body(Collections.singletonMap("code", "FeignException"));
    }

    @ExceptionHandler(NoFallbackAvailableException.class)
    public ResponseEntity<?> handleNoFallbackAvailableException(NoFallbackAvailableException e) {
        return ResponseEntity.badRequest()
                .body(Collections.singletonMap("code", "NoFallbackAvailableException"));
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<?> handleCallNotPermittedException(CallNotPermittedException e) {
        return ResponseEntity.internalServerError()
                .body(Collections.singletonMap("code", "CallNotPermittedException"));
    }

}
