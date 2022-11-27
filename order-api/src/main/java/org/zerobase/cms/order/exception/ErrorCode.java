package org.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    ERROR_SAMPLE(HttpStatus.BAD_REQUEST, "");

    private final HttpStatus httpStatus;
    private final String message;
}
