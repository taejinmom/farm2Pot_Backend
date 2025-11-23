package com.farm2pot.product.controller.dto;

import com.farm2pot.product.enums.StockType;

import java.time.LocalDateTime;

public record ProductHistoryResponse(
        Long historyId,
        String productCode,
        String productName,
        StockType adjustmentType,
        Integer quantity,
        Integer previousQty,
        Integer currentQty,
        String reason,
        String createdBy,
        LocalDateTime createdAt
) {
}
