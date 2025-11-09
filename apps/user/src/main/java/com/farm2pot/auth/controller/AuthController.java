package com.farm2pot.auth.controller;

import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.auth.service.AuthService;
import com.farm2pot.auth.controller.dto.LoginRequest;
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
    public LoginTokenResponse refresh(@RequestBody TokenRefresh request) {
        return authService.refresh(request);
    }

    // 로그인
    @PostMapping("/login")
    public LoginTokenResponse login(
            @RequestBody LoginRequest loginRequest, HttpServletResponse response
    ) {
        LoginTokenResponse loginResponse = authService.login(loginRequest, response);
        return loginResponse;
    }

    // 회원가입
    @PostMapping("/register")
    public void register( @RequestBody @Validated CreateUserRequest userDto ) {
        authService.register(userDto); // 실제 회원가입 처리
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

}