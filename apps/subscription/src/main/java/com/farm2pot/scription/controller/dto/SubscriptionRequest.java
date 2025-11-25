package com.farm2pot.scription.controller.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record SubscriptionRequest(
        Long userId,
        int price,
        LocalDate startDate,
        int months
) {}