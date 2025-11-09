package com.farm2pot.user.service;

import com.farm2pot.auth.repository.RefreshTokenRepository;
import com.farm2pot.common.exception.BaseException;
import com.farm2pot.common.exception.UserErrorCode;
import com.farm2pot.common.service.CommonService;
import com.farm2pot.user.controller.dto.EditUserRequest;
import com.farm2pot.user.entity.User;
import com.farm2pot.user.mapper.EditUserMapper;
import com.farm2pot.user.repository.UserRepository;
import com.farm2pot.user.service.dto.UserPasswordCheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private final CommonService commonService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EditUserMapper editUserMapper;

    /**
     * 로그아웃 (Refresh Token 제거)
     */
    @Transactional
    public void logout(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        refreshTokenRepository.deleteByUserId(user.getId());
    }

    /**
     * 사용자 정보 조회 (loginId)
     */
    public User findByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        return user;
    }
    /**
     * 사용자 정보 조회 (id)
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaseException(USER_NOT_FOUND));
    }



    /**
     * 사용자 정보 수정 (프로필 수정)
     */
    @Transactional
    public User editUserInfo(EditUserRequest editUserRequest) {
        // 기존 user Data -> Id로 조회 할 것
        User user = userRepository.findById(editUserRequest.id())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        //패스워드 변경이 있었는지 확인
        String password = editUserRequest.password();

        EditUserRequest updateUserInfo = editUserRequest;
        if(!StringUtils.isEmpty(password)){
            if(commonService.matches(password, user.getPassword())){
                updateUserInfo = editUserRequest.toBuilder()
                        .password(commonService.encodePassword(password))
                        .build();
            }
        }

        //Dto to Entity
        editUserMapper.updateEntityFromDto(
                updateUserInfo, user);
        return user;
    }


    /**
     * 사용자 삭제 (user - pk)
     * @param id
     */
    public void deleteUserById(Long id){
        if (!userRepository.existsById(id)) new BaseException(USER_NOT_FOUND);
        userRepository.deleteById(id);
    }


    /**
     * 패스워드 체크
     */
    public boolean validatePassword(String oldPassword, String newPassword ) {
        return Optional.of(commonService.matches(newPassword, oldPassword))
                .filter(result -> result) // true일 때만 통과
                .orElseThrow(() -> new BaseException(INVALID_PASASWORD));
    }

    /**
     * 사용자 패스워드 체크
     * @param userPasswordCheckDto
     * @return
     */
    public boolean checkUser(UserPasswordCheckDto userPasswordCheckDto) {
        User user = userRepository.findById(userPasswordCheckDto.id()).orElseThrow(() -> new BaseException(UserErrorCode.UNAUTHORIZED_USER));
        return validatePassword(user.getPassword(), userPasswordCheckDto.password());
    }
}

