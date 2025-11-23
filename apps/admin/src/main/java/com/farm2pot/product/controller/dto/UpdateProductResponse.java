package com.farm2pot.product.controller.dto;

import java.time.LocalDateTime;

public record UpdateProductResponse(
        Long productId,
        String code,
        String name,
        LocalDateTime updatedAt
) {
}
