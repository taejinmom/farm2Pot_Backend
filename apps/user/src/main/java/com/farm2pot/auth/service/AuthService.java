package com.farm2pot.auth.service;


import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.mapper.AddressMapper;
import com.farm2pot.address.repository.AddressRepository;
import com.farm2pot.address.service.AddressService;
import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.auth.controller.dto.LoginRequest;
import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.entity.RefreshToken;
import com.farm2pot.auth.mapper.CreateUserMapper;
import com.farm2pot.auth.mapper.RefreshTokenMapper;
import com.farm2pot.auth.repository.RefreshTokenRepository;
import com.farm2pot.auth.service.dto.LoginTokenResponse;
import com.farm2pot.common.exception.BaseException;
import com.farm2pot.utils.exception.UserErrorCode;
import com.farm2pot.security.service.JwtProvider;
import com.farm2pot.user.controller.dto.UserResponse;
import com.farm2pot.user.entity.User;
import com.farm2pot.user.mapper.UserMapper;
import com.farm2pot.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.farm2pot.utils.exception.UserErrorCode.INTERNAL_SERVER_ERROR;
import static com.farm2pot.utils.exception.UserErrorCode.USER_NOT_FOUND;

/**
 * packageName    : com.farm2pot.auth.service
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;
    private final AddressMapper userAddressMapper;
    private final AddressService addressService;

    /**
     * Refresh Token을 이용한 Access Token 재발급
     */
    public LoginTokenResponse refresh(TokenRefresh request) {
        // 1. 토큰 존재 확인
        RefreshToken tokenEntity = refreshTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new BaseException(UserErrorCode.INVALID_TOKEN));

        // 2. 만료 여부 확인
        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(tokenEntity);
            throw new BaseException(UserErrorCode.EXPIRED_TOKEN);
        }

        // 3. 사용자 정보 확인
        User user = userRepository.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 4. 새 Access Token 발급
        String newAccessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole().name());

        return new LoginTokenResponse(user.getId(), newAccessToken, request.token());
    }

    /**
     * 로그인 처리
     */
    @Transactional
    public LoginTokenResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        // 1. 로그인 ID 확인
        User user = userRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BaseException(UserErrorCode.INVALID_CREDENTIALS);
        }

        // 3. Access Token & Refresh Token 발급
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole().name());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        // 4. 기존 Refresh Token 삭제 (중복 방지)
        refreshTokenRepository.deleteByUserId(user.getId());

        // 5. Refresh Token 저장
        TokenRefresh dto = new TokenRefresh(
                null,
                refreshToken,
                user.getId(),
                Instant.now().plusMillis(604800000)
        );
        refreshTokenRepository.save(refreshTokenMapper.toEntity(dto));

        // 6. response Header에 등록
        response.setHeader("X-USER-ID", user.getId().toString());

        //결과 반환
        return new LoginTokenResponse(user.getId(), accessToken, refreshToken);
    }

    /**
     * 회원가입
     */
    @Transactional
    public void register(CreateUserRequest createUserRequest){
        //1. 사용자정보 Insert
        User user = createUserMapper.toEntity(
                createUserRequest.toBuilder()
                        .password(passwordEncoder.encode(createUserRequest.password()))
                        .build()
        );
        userRepository.save(user);

        //2. 주소정보 Insert
        addressRepository.save(userAddressMapper.toEntity(setUserAddress(createUserRequest, user)));
    }

    // 전체 사용자 조회
    public List<User> getAllUsers() {
        return Optional.of(userRepository.findAll()).orElseThrow(() -> new BaseException(INTERNAL_SERVER_ERROR));
    }


    /**
     * 회원가입 시 입력한 주소정보를 default로 처리
     * @param createUserRequest
     * @return
     */
    public AddressData setUserAddress(CreateUserRequest createUserRequest, User user) {
        AddressData addressData = createUserRequest.address();
        return addressData.toBuilder()
                .isDefault(true)
                .user(user)
                .build();
    }

}