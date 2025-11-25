package com.farm2pot.user.service.dto;

import lombok.*;

@Builder(toBuilder = true)
public record CheckUserPassword(
    Long id,
    String password,   // request에 있는 password
    String loginId,
    String oldPassword
){}
