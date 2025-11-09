package com.farm2pot.address.service.dto;

import com.farm2pot.user.entity.User;

import java.time.LocalDateTime;

/**
 * packageName    : com.farm2pot.address.service.dto
 * author         : TAEJIN
 * date           : 2025-11-07
 * description    :
 */
public record DefaultAddressResponse (
        Long id,
        Long userId,
        String loginId,
        String recipientName,
        String phoneNumber,
        String postalCode,
        String addressLine1,
        String addressLine2,
        boolean isDefault,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
