package com.farm2pot.product.service;

import com.farm2pot.common.exception.BusinessErrorCode;
import com.farm2pot.common.exception.BusinessException;
import com.farm2pot.product.controller.dto.*;
import com.farm2pot.product.entity.Product;
import com.farm2pot.product.entity.ProductHistory;
import com.farm2pot.product.enums.StockType;
import com.farm2pot.product.controller.dto.mapper.ProductDtoMapper;
import com.farm2pot.product.repository.ProductHistoryRepository;
import com.farm2pot.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final ProductDtoMapper productDtoMapper;

    /**
     * 상품 목록 조회
     */
    public Page<ProductResponse> getProducts(String keyword, String category, String origin, Pageable pageable) {
        Page<Product> products = productRepository.getProducts(keyword, category, origin, pageable);
        return products.map(productDtoMapper::toProductDto);
    }

    /**
     * 상품 상세 조회
     */
    public ProductResponse getProduct(String productCode) {
        Product product = productRepository.findByCode(productCode)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));
        return productDtoMapper.toProductDto(product);
    }

    /**
     * 상품 등록
     */
    @Transactional
    public CreateProductResponse createProduct(CreateProductRequst request) {
        String productCode = request.code();
        if (productCode == null || productCode.isBlank()) {
            productCode = generateProductCode();
        }

        Product product = Product.create(
                productCode,
                request.name(),
                request.price(),
                request.weight(),
                request.origin(),
                request.category(),
                request.initialQty()
        );

        Product savedProduct = productRepository.save(product);

        return productDtoMapper.toCreateProductDto(savedProduct);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public UpdateProductResponse updateProduct(Long productId, UpdateProductRequst request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));

        product.update(
                request.code(),
                request.name(),
                request.price(),
                request.weight(),
                request.origin(),
                request.category()
        );

        return productDtoMapper.toUpdateProductDto(product);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);

        return LocalDateTime.now().toString();
    }

    /**
     * 재고 수량 조정
     */
    @Transactional
    public StockAdjustmentResponse adjustStock(Long productId, StockAdjustmentRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRODUCT_NOT_FOUND));

        int previousQty = product.getStockQuantity();

        if (request.adjustmentType() == StockType.INCREASE) {
            product.increaseStock(request.quantity());
        } else if (request.adjustmentType() == StockType.DECREASE) {
            product.decreaseStock(request.quantity());
        }

        int currentQty = product.getStockQuantity();

        ProductHistory history = ProductHistory.builder()
                .product(product)
                .adjustmentType(request.adjustmentType())
                .quantity(request.quantity())
                .previousQty(previousQty)
                .currentQty(currentQty)
                .reason(request.reason())
                .build();

        productHistoryRepository.save(history);

        return StockAdjustmentResponse.of(
                product,
                previousQty,
                currentQty,
                request.quantity(),
                request.adjustmentType()
        );
    }

    /**
     * 재고 이력 조회
     */
    public Page<ProductHistoryResponse> getProductHistory(
            Long productId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        Page<ProductHistory> histories;

        if (startDate != null && endDate != null) {
            histories = productHistoryRepository.findByProductIdAndCreateAtBetween(
                    productId, startDate, endDate, pageable
            );
        } else {
            histories = productHistoryRepository.findByProductId(productId, pageable);
        }

        return histories.map(productDtoMapper::toProductHistoryDto);
    }

    /**
     * 상품 코드 생성
     */
    private String generateProductCode() {
        return "PRD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
