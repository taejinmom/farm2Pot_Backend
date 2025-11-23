package com.farm2pot.product.controller.dto;

import com.farm2pot.product.enums.StockType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockAdjustmentRequest(
        @NotNull(message = "조정 유형은 필수입니다")
        StockType adjustmentType,

        @NotNull(message = "조정 수량은 필수입니다")
        @Positive(message = "조정 수량은 양수여야 합니다")
        Integer quantity,

        String reason
) {
}
