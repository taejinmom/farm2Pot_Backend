package com.farm2pot.address.service.dto;

import com.farm2pot.address.entity.Address;

/**
 * packageName    : com.farm2pot.user.service.dto
 * author         : TAEJIN
 * date           : 2025-11-06
 * description    : 사용자의 기본 배송지 정보
 */
public record UserWithDefaultAddress(
        Long userId,
        String loginId,
        String recipientName,
        String addressLine1,
        String addressLine2,
        String phoneNumber,
        Address address
) {}
