package com.farm2pot.scription.service.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record SubscriptionResponse(
        Long id,
        Long userId,
        int price,
        LocalDate startDate,
        LocalDate endDate,
        String status
){}
