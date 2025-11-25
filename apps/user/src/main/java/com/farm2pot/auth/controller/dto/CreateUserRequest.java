package com.farm2pot.auth.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.user.entity.Role;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreateUserRequest (
        Long id,
        String loginId,
        String password,
        String email,
        String name,
        int status,
        Role role,
        AddressData address // 기본 배송지
){}
