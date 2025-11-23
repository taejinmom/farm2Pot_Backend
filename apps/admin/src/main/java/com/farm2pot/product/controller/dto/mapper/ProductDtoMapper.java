package com.farm2pot.product.controller.dto.mapper;

import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.product.controller.dto.*;
import com.farm2pot.product.entity.Product;
import com.farm2pot.product.entity.ProductHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface ProductDtoMapper {

    @Mapping(source = "id", target = "productId")
    @Mapping(target = "qty", expression = "java(product.getStockQuantity())")
    @Mapping(target = "imageUrl", constant = "")
    @Mapping(source = "createAt", target = "createdAt")
    @Mapping(source = "updateAt", target = "updatedAt")
    ProductResponse toProductDto(Product product);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "createAt", target = "createdAt")
    CreateProductResponse toCreateProductDto(Product product);

    @Mapping(source = "id", target = "productId")
    @Mapping(source = "updateAt", target = "updatedAt")
    UpdateProductResponse toUpdateProductDto(Product product);

    @Mapping(source = "id", target = "historyId")
    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "createUserId", target = "createdBy")
    @Mapping(source = "createAt", target = "createdAt")
    ProductHistoryResponse toProductHistoryDto(ProductHistory history);
}
