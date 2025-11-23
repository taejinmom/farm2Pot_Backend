package com.farm2pot.product.controller;

import com.farm2pot.common.http.request.CustomPageRequest;
import com.farm2pot.product.controller.dto.*;
import com.farm2pot.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 목록 조회
     */
    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String origin,
            CustomPageRequest pageRequest
    ) {
        return productService.getProducts(keyword, category, origin, pageRequest.getPageable());
    }

    /**
     * 상품 상세 조회
     */
    @GetMapping("/{productCode}")
    public ProductResponse getProduct(@PathVariable String productCode) {
        return productService.getProduct(productCode);
    }

    /**
     * 상품 등록
     */
    @PostMapping
    public CreateProductResponse createProduct(@Valid @RequestBody CreateProductRequst request) {
        return productService.createProduct(request);
    }

    /**
     * 상품 정보 수정
     */
    @PutMapping("/{productId}")
    public UpdateProductResponse updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequst request
    ) {
        return productService.updateProduct(productId, request);
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    /**
     * 재고 수량 조정
     */
    @PatchMapping("/{productId}")
    public StockAdjustmentResponse adjustStock(
            @PathVariable Long productId,
            @Valid @RequestBody StockAdjustmentRequest request
    ) {
        return productService.adjustStock(productId, request);
    }

    /**
     * 재고 이력 조회
     */
    @GetMapping("/{productId}/history")
    public Page<ProductHistoryResponse> getProductHistory(
            @PathVariable Long productId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            CustomPageRequest pageRequest
    ) {
        return productService.getProductHistory(productId, startDate, endDate, pageRequest.getPageable());
    }
}
