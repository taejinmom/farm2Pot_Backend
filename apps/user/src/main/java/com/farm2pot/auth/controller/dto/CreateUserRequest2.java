package com.farm2pot.auth.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;

import java.util.Date;
import java.util.List;

public record CreateUserRequest2 (
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
        AddressData addressData // 사용자 주소
){
}
