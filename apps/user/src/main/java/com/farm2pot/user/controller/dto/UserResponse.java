package com.farm2pot.user.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.service.dto.DefaultAddressResponse;
import lombok.Builder;

import java.util.Date;
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
        String loginType,
        String phoneNo,
        Date birthDay,
        int status,
        String gender,
        String nickName,
        List<String> roles,
        List<DefaultAddressResponse> addresses
) {}
