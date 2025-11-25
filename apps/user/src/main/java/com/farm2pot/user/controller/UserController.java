package com.farm2pot.user.controller;

import com.farm2pot.user.controller.dto.EditUserRequest;
import com.farm2pot.user.entity.User;
import com.farm2pot.user.service.UserService;
import com.farm2pot.user.service.dto.CheckUserPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그아웃
    @DeleteMapping("/logout")
    public void logout(@RequestParam("id") Long id ) {
        userService.logout(id);
    }

    /**
     * 사용자 정보 수정
     * @param editUserRequest
     * @return
     */
    @PatchMapping("/edit")
    public User editUser(@RequestBody EditUserRequest editUserRequest) {
        return userService.editUserInfo(editUserRequest);
    }

    /**
     * 사용자 삭제 - id
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }


    /**
     * 사용자 탈퇴 - id
     * @param id
     */
    @PatchMapping("/deactivate/{id}")
    public void deactivateUser(@PathVariable("id") Long id){

    }
    /**
     * 사용자 정보 확인 - loginId
     * @param loginId
     * @return
     */
    @GetMapping("/userinfo-lid/{loginId}")
    public User getUserInfoByLoginId(@PathVariable("loginId") String loginId) {
        return userService.findByLoginId(loginId);
    }

    /**
     * 사용자 정보 확인 - id
     * @param id
     * @return
     */
    @GetMapping("/userinfo-uid/{id}")
    public User getUserInfoByUserId(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    /**
     * 사용자 패스워드 체크.. 마이페이지 접근 시 필요
     * X-USER-ID, 변경패스워드 필요
     * @param checkUserPassword
     * @return boolean
     */
    @PostMapping("/check-password")
    public boolean checkPassword(@RequestBody CheckUserPassword checkUserPassword) {
            return userService.checkUser(checkUserPassword);
    }
}
