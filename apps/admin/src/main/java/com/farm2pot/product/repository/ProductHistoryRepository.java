package com.farm2pot.product.repository;

import com.farm2pot.product.entity.ProductHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    Page<ProductHistory> findByProductId(Long productId, Pageable pageable);

    Page<ProductHistory> findByProductIdAndCreateAtBetween(
            Long productId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
