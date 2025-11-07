package com.farm2pot.auth.service;


import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.entity.Address;
import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.auth.service.dto.LoginTokenResponse;
import com.farm2pot.user.controller.dto.LoginRequest;
import com.farm2pot.user.controller.dto.UserDto;
import com.farm2pot.auth.entity.RefreshToken;
import com.farm2pot.user.entity.User;
import com.farm2pot.auth.mapper.RefreshTokenMapper;
import com.farm2pot.address.mapper.AddressMapper;
import com.farm2pot.user.mapper.CreateUserMapper;
import com.farm2pot.user.mapper.UserMapper;
import com.farm2pot.auth.repository.RefreshTokenRepository;
import com.farm2pot.address.repository.AddressRepository;
import com.farm2pot.user.repository.UserRepository;
import com.farm2pot.common.exception.UserException;
import com.farm2pot.common.exception.UserErrorCode;
import com.farm2pot.security.service.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.farm2pot.common.exception.UserErrorCode.INTERNAL_SERVER_ERROR;
import static com.farm2pot.common.exception.UserErrorCode.USER_NOT_FOUND;

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

    /**
     * Refresh Token을 이용한 Access Token 재발급
     */
    public LoginTokenResponse refresh(TokenRefresh request) {
        // 1. 토큰 존재 확인
        RefreshToken tokenEntity = refreshTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_TOKEN));

        // 2. 만료 여부 확인
        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(tokenEntity);
            throw new UserException(UserErrorCode.EXPIRED_TOKEN);
        }

        // 3. 사용자 정보 확인
        User user = userRepository.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        // 4. 새 Access Token 발급
        String newAccessToken = jwtProvider.generateAccessToken(user.getId(), user.getRoles());

        return new LoginTokenResponse(user.getId(), newAccessToken, request.token(), userMapper.toDto(user));
    }

    /**
     * 로그인 처리
     */
    @Transactional
    public LoginTokenResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        // 1. 로그인 ID 확인
        User user = userRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_CREDENTIALS);
        }

        // 3. Access Token & Refresh Token 발급
        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRoles());
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

        // 5. 결과 반환
        return new LoginTokenResponse(user.getId(), accessToken, refreshToken, userMapper.toDto(user));
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
        addressRepository.save(userAddressMapper.toEntity(getUserAddress(createUserRequest, user)));
    }

    // 전체 사용자 조회
    public List<User> getAllUsers() {
        return Optional.of(userRepository.findAll()).orElseThrow(() -> new UserException(INTERNAL_SERVER_ERROR));
    }

    public void init() {
        UserDto dto = UserDto.builder()
                .loginId("xclick")
                .email("taejin1@example.com")
                .password(passwordEncoder.encode("a"))
                .name("김태진")
                .loginType("LOCAL")
                .phoneNo("010-1234-5678")
                .status(1)
                .gender("M")
                .nickName("농부태진")
                .roles(List.of("ROLE_USER","ROLE_ADMIN"))
                .build();

        User entity = userMapper.toEntity(dto);
        userRepository.save(entity);
    }

    /**
     * 회원가입 시 입력한 주소정보를 default로 처리
     * @param createUserRequest
     * @return
     */
    public AddressData getUserAddress(CreateUserRequest createUserRequest, User user) {
        AddressData addressData = createUserRequest.addressData();
        return addressData.toBuilder()
                .isDefault(true)
                .user(user)
                .build();
    }

//    @PostConstruct
    public void initInsertData() {
        init();
    }
}