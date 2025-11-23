package com.farm2pot.product.controller.dto;

import java.time.LocalDateTime;

public record CreateProductResponse(
        Long productId,
        String code,
        String name,
        LocalDateTime createdAt
) {
}
