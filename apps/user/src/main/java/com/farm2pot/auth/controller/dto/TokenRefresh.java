package com.farm2pot.auth.controller.dto;

import lombok.*;

import java.time.Instant;

public record TokenRefresh(
        Long id,
        String token,
        Long userId,
        Instant expiryDate
) {}
