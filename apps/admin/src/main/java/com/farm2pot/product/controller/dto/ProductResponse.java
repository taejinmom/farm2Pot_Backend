package com.farm2pot.product.controller.dto;

import java.time.LocalDateTime;

public record ProductResponse(
        Long productId,
        String code,
        String name,
        Integer price,
        Integer qty,
        String weight,
        String category,
        String origin,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
