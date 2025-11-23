package com.farm2pot.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequst(
        @NotBlank(message = "상품명은 필수입니다")
        String name,

        String code,

        @NotNull(message = "상품 가격은 필수입니다")
        Integer price,

        @NotBlank(message = "상품 무게는 필수입니다")
        String weight,

        @NotBlank(message = "원산지는 필수입니다")
        String origin,

        @NotBlank(message = "카테고리는 필수입니다")
        String category,

        Integer initialQty
) {
}
