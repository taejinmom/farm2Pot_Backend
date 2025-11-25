package com.farm2pot.user.controller.dto;

import com.farm2pot.address.service.dto.DefaultAddressResponse;
import com.farm2pot.user.entity.Role;
import lombok.Builder;

import java.util.List;

/**
 * packageName    : com.farm2pot.user.controller.dto
 * author         : TAEJIN
 * date           : 2025-11-06
 * description    :
 */

@Builder(toBuilder = true)
public record UserResponse (
        Long id,
        String loginId,
        String email,
        String password,
        String name,
        int status,
        Role role,
        List<DefaultAddressResponse> addressesData
) {}
