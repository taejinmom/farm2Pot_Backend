package com.farm2pot.product.entity;

import com.farm2pot.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Integer price;
    private String weight;

    @Embedded
    private Stock stock;

    private String origin;
    private String category;

    /**
     * 상품 생성 (정적 팩토리 메서드)
     */
    public static Product create(String code, String name, Integer price, String weight,
                                 String origin, String category, Integer initialQty) {
        Stock stock = Stock.builder()
                .quantity(new AtomicInteger(initialQty != null ? initialQty : 0))
                .build();

        return new Product(null, code, name, price, weight, stock, origin, category);
    }

    /**
     * 상품 정보 수정
     */
    public void update(String code, String name, Integer price, String weight,
                      String origin, String category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.origin = origin;
        this.category = category;
    }

    /**
     * 재고 증가
     */
    public void increaseStock(Integer amount) {
        this.stock.increase(new AtomicInteger(amount));
    }

    /**
     * 재고 감소
     */
    public void decreaseStock(Integer amount) {
        this.stock.decrease(new AtomicInteger(amount));
    }

    /**
     * 현재 재고 수량 조회
     */
    public int getStockQuantity() {
        return this.stock.getQuantity().get();
    }
}
