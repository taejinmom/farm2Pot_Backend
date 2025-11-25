package com.farm2pot.scription.repository;

import com.farm2pot.scription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Long userId);
    List<Subscription> findByEndDateBeforeAndStatus(LocalDate today, String status);
    List<Subscription> findByStartDateAndStatus(LocalDate startDate, String status);
    List<Subscription> findByStartDateAndStatusNot(LocalDate startDate, String status);
}
