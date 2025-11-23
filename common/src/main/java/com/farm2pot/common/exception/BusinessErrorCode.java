package com.farm2pot.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorCode {

    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다")
    ;

    private final String message;
}
