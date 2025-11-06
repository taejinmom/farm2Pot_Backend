package com.farm2pot.address.controller.dto;

import com.farm2pot.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 * packageName    : com.farm2pot.user.dto
 * author         : TAEJIN
 * date           : 2025-10-14
 * description    :
 */


@Builder(toBuilder = true)
public record AddressData (
        Long id,
        Long userId,
        User user,
        String loginId,

        String recipientName,
        String phoneNumber,
        String postalCode,
        String addressLine1,
        String addressLine2,
        boolean isDefault,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

){

}
