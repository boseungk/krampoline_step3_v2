package com.theocean.fundering.global.errors.exception;

import com.theocean.fundering.global.utils.ApiResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    public Exception400(final String message) {
        super(message);
    }

    public ApiResult<?> body() {
        return ApiResult.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}