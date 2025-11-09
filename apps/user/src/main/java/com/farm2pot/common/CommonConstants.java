package com.farm2pot.common;

/**
 * packageName    : com.farm2pot.common
 * author         : TAEJIN
 * date           : 2025-11-08
 * description    :
 */
public enum CommonConstants {
    /* User */
    /* role 관련*/
        USER_ROLE_ADMIN("ROLE_ADMIN"),
        USER_ROLE_USER("ROLE_USER"),
        USER_ROLE_GUEST("ROLE_GUEST"),

    /* 계정 활성화 여부 */
        USER_STATUS_1("1"), // 활성화
        USER_STATUS_2("2"), // 비활성화

    /* 사용자 성별 */
        USER_GENDER_M("M"),
        USER_GENDER_W("W"),

    /* 사용자 로그인 TYPE */
        USER_LOGIN_TYPE_KAKAO("KAKAO"),
        USER_LOGIN_TYPE_LOCAL("LOCAL"),
        USER_LOGIN_TYPE_GOOGLE("GOOGLE"),

    /* Address*/
        ADDRESS_LOGIN_TYPE_GOOGLE("GOOGLE");


    private final String code;

    CommonConstants(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public int asInt() {
        return Integer.parseInt(code);
    }
}
