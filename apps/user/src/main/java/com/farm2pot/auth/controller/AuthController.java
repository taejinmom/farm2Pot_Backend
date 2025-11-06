package com.farm2pot.auth.controller;

import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.auth.service.AuthService;
import com.farm2pot.common.response.ResponseMessage;
import com.farm2pot.user.controller.dto.LoginRequest;
import com.farm2pot.auth.service.dto.LoginTokenResponse;
import com.farm2pot.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.farm2pot.auth.controller
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */
@RestController
@RequestMapping("/api/user/public")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseMessage<LoginTokenResponse> refresh(@RequestBody TokenRefresh request) {
        return ResponseMessage.success("token refresh ..... success", authService.refresh(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseMessage<LoginTokenResponse> login(
            @RequestBody LoginRequest loginRequest, HttpServletResponse response
    ) {
        LoginTokenResponse loginResponse = authService.login(loginRequest, response);
        return ResponseMessage.success("login success", loginResponse);
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseMessage<String> register(
            @RequestBody @Validated CreateUserRequest userDto // TODO : -> 회원가입용 requestDTO 추가해야 함.
    ) {
        authService.register(userDto); // 실제 회원가입 처리
        return ResponseMessage.success("join success", "");
    }

    @GetMapping("/users")
    public ResponseMessage<List<User>> getAllUsers() {
        return ResponseMessage.success("select All Users" , authService.getAllUsers());
    }

}