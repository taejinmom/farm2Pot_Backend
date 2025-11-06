package com.farm2pot.user.service;

import com.farm2pot.address.service.AddressService;
import com.farm2pot.auth.repository.RefreshTokenRepository;
import com.farm2pot.common.exception.UserErrorCode;
import com.farm2pot.common.exception.UserException;
import com.farm2pot.common.service.CommonService;
import com.farm2pot.user.controller.dto.UserDto;
import com.farm2pot.user.service.dto.UserPasswordCheckDto;
import com.farm2pot.user.entity.User;
import com.farm2pot.user.mapper.UserMapper;
import com.farm2pot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.farm2pot.common.exception.UserErrorCode.INVALID_PASASWORD;
import static com.farm2pot.common.exception.UserErrorCode.USER_NOT_FOUND;

/**
 * packageName    : com.farm2pot.user.service
 * author         : TAEJIN
 * date           : 2025-10-13
 * description    :
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final CommonService commonService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;

    /**
     * 로그아웃 (Refresh Token 제거)
     */
    @Transactional
    public void logout(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        refreshTokenRepository.deleteByUserId(user.getId());
    }

    /**
     * 사용자 정보 조회 (loginId)
     */
    public User findByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return user;
    }
    /**
     * 사용자 정보 조회 (id)
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    /**
     * 사용자 정보 수정 (프로필 수정)
     */
    @Transactional
    public User editUserInfo(UserDto userDto) {
        // 기존 user Data -> Id로 조회 할 것
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        //패스워드 변경이 있었는지 확인
        String password = Optional.ofNullable(userDto)
                .map(UserDto::getPassword)
                .orElse(null);

        String ecodedPassword = commonService.encodePassword(password);
        userDto.setPassword(ecodedPassword);

        //Dto to Entity
        userMapper.updateEntityFromDto(
                userDto, user);
        return user;
    }


    /**
     * 사용자 삭제 (user - pk)
     * @param id
     */
    public void deleteUserById(Long id){
        if (userRepository.existsById(id)) userRepository.deleteById(id);
    }


    /**
     * 패스워드 체크
     */
    public boolean validatePassword(String oldPassword, String newPassword ) {
        return Optional.of(commonService.matches(newPassword, oldPassword))
                .filter(result -> result) // true일 때만 통과
                .orElseThrow(() -> new UserException(INVALID_PASASWORD));
    }

    /**
     * 사용자 패스워드 체크
     * @param userPasswordCheckDto
     * @return
     */
    public boolean checkUser(UserPasswordCheckDto userPasswordCheckDto) {
        User user = userRepository.findById(userPasswordCheckDto.id()).orElseThrow(() -> new UserException(UserErrorCode.UNAUTHORIZED_USER));
        return validatePassword(user.getPassword(), userPasswordCheckDto.password());
    }
}

