package com.farm2pot.user.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
public record EditUserRequest(
        Long id,
        String loginId,
        String password,
        String email,
        String name,
        String loginType,
        String phoneNo,
        Date birthDay,
        int status,
        String gender,
        String nickName,
        List<String> roles,
        AddressData addressData // 기본 배송지
){}
