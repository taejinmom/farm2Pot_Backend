package com.farm2pot.product.controller.dto;

import com.farm2pot.product.service.dto.ProductsQueryCommand;

public record ProductsRequest(String name) {

    public ProductsQueryCommand toCommand() {
        return new ProductsQueryCommand();
    }
}
