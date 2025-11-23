package com.farm2pot.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode {

    OUT_OF_STOCK("재고가 부족합니다.")
    ;

    private final String message;
}
