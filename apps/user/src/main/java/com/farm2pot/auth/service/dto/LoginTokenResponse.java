package com.farm2pot.auth.service.dto;

import com.farm2pot.user.controller.dto.UserDto;
import lombok.*;

@Builder(toBuilder = true)
public record LoginTokenResponse (
    Long userId,
    String accessToken,
    String refreshToken,
    UserDto userDto

){}
