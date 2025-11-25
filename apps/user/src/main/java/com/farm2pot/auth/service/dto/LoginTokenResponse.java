package com.farm2pot.auth.service.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginTokenResponse (
    Long userId,
    String accessToken,
    String refreshToken
){}
