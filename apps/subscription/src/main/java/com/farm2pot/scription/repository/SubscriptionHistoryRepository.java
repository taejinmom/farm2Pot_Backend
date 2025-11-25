package com.farm2pot.scription.repository;

import com.farm2pot.scription.entity.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Long> {
    @Query("SELECT h FROM SubscriptionHistory h " +
            "WHERE h.userId = :userId " +
            "AND h.status = 'ACTIVE'")
    Optional<SubscriptionHistory> findActiveHistoryByUserId(@Param("userId") Long userId);
    @Query("""
        SELECT h FROM SubscriptionHistory h
        WHERE h.userId = :userId
        AND h.status = :status
        AND h.startDate = :today
        ORDER BY h.startDate ASC
    """)
    List<SubscriptionHistory> findPromotableReserved(
            @Param("userId") Long userId,
            @Param("status") String status,
            @Param("today") LocalDate today
    );

    @Query("""
        SELECT h
        FROM SubscriptionHistory h
        WHERE h.status = :status
          AND h.startDate = :startDate
          AND NOT EXISTS (
                SELECT s
                FROM Subscription s
                WHERE s.userId = h.userId
                  AND s.status = 'ACTIVE'
          )
    """)
    List<SubscriptionHistory> findReservedHistoryWithoutActiveSubscription(
            @Param("status") String status,
            @Param("startDate") LocalDate startDate
    );
}
