package com.example.searchplace.feign;

import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

@Component
public class RecordFailurePredicate implements Predicate<Throwable> {


    //반환값이 True면 Fail로 기록되어 서킷 open 처리됨. 어떤 Exception에 한하여 Circuit을 자동 open 처리할지 설정.
    //TimeoutException, FeignException.FeignServerException 두개일때만 true 처리
    @Override
    public boolean test(Throwable t) {
        if (t instanceof TimeoutException) {
            return true;
        }
        if (t instanceof FeignException.FeignServerException) {
            return true;
        }
        return t instanceof FeignException.FeignServerException;
    }
}
