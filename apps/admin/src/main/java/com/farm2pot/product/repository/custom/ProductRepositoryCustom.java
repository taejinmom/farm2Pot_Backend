package com.farm2pot.product.repository.custom;

import com.farm2pot.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getProducts(String keyword, String category, String origin, Pageable pageable);
}
