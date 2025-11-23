package com.farm2pot.product.controller.dto;

import com.farm2pot.product.entity.Product;
import com.farm2pot.product.enums.StockType;

import java.time.LocalDateTime;

public record StockAdjustmentResponse(
        Long productId,
        String code,
        String name,
        Integer previousQty,
        Integer currentQty,
        Integer adjustmentQty,
        StockType adjustmentType,
        LocalDateTime updatedAt
) {
    public static StockAdjustmentResponse of(
            Product product,
            Integer previousQty,
            Integer currentQty,
            Integer adjustmentQty,
            StockType adjustmentType
    ) {
        return new StockAdjustmentResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                previousQty,
                currentQty,
                adjustmentQty,
                adjustmentType,
                LocalDateTime.now()
        );
    }
}
