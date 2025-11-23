package com.farm2pot.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockType {
    INCREASE("증가"),
    DECREASE("감소");

    private final String description;
}