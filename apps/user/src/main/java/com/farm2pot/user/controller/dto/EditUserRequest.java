package com.farm2pot.user.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.user.entity.Role;
import lombok.Builder;

@Builder(toBuilder = true)
public record EditUserRequest(
        Long id,
        String loginId,
        String password,
        String email,
        String name,
        int status,
        Role role,
        AddressData addressData // 기본 배송지
){}
