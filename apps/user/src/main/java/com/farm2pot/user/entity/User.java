package com.farm2pot.user.entity;

import com.farm2pot.address.entity.Address;
import com.farm2pot.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * packageName    : com.farm2pot.auth.entity
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */
@Entity
@Table(name = "user")  // DB 테이블명
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 PK

    @Column(nullable = false, unique = true)
    private String loginId;   // 사용자 아이디 (unique)

    @Column(nullable = false, unique = true)
    private String email;    // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호 (bcrypt 암호화 저장)

    private String name;     // 이름

    private String loginType; // 로그인 방식 (local, google, kakao 등)

    private String phoneNo;   // 전화번호

    @Temporal(TemporalType.DATE)
    private Date birthDay;    // 생일

    private int status;       // 상태 (0=비활성, 1=활성 등)

    private String gender;    // 성별

    private String nickName;  // 닉네임

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "roles")
    private List<String> roles = new ArrayList<>();      // 권한 (ROLE_USER, ROLE_ADMIN 등)

    // 1:N 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses;

}
