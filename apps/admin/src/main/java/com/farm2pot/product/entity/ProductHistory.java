package com.farm2pot.product.entity;

import com.farm2pot.common.jpa.BaseEntity;
import com.farm2pot.product.enums.StockType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private StockType adjustmentType;

    private Integer quantity;
    private Integer previousQty;
    private Integer currentQty;
    private String reason;
}
