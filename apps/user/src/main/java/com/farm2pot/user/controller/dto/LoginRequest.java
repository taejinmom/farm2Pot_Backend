package com.farm2pot.user.controller.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginRequest (
    String loginId,
    String password
){}
