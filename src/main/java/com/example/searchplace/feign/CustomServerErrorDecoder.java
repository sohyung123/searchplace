package com.example.searchplace.feign;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class CustomServerErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if(HttpStatus.valueOf(status).is5xxServerError()) {
            return new RetryableException(status, format("%s 요청이 성공하지 못했습니다. Retry 합니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()),
                    response.request().httpMethod(), feignException, null, response.request());
        }
        return feignException;
    }
}
