package com.farm2pot.user.controller.dto;

import com.farm2pot.address.controller.dto.AddressData;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * packageName    : com.farm2pot.auth.dto
 * author         : TAEJIN
 * date           : 2025-10-13
 * description    : 사용자 정보 DTO
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String loginId;
    private String email;
    private String password;
    private String name;
    private String loginType;
    private String phoneNo;
    private Date birthDay;
    private int status;
    private String gender;
    private String nickName;
    private List<String> roles;
    private List<AddressData> userAddress;
}
