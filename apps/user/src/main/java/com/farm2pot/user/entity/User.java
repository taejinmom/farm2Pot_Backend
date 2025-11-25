package com.farm2pot.user.entity;

import com.farm2pot.address.entity.Address;
import com.farm2pot.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    private int status;       // 상태 (0=비활성, 1=활성 등)

    @Enumerated(EnumType.STRING)
    private Role role;

    // 1:N 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses;

}
