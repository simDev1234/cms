package org.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "해당 상품이 없습니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST, "동일 상품이 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
