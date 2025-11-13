package com.farm2pot.address.entity;

import com.farm2pot.common.jpa.BaseEntity;
import com.farm2pot.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * packageName    : com.farm2pot.user.entity
 * author         : TAEJIN
 * date           : 2025-10-14
 * description    :
 */
@Entity
@Table(name = "user_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // N:1 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id", // FK 컬럼 이름을 PK와 다르게 지정
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @JsonBackReference     // 반대쪽은 직렬화에서 제외
    private User user;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "is_default" )
    private boolean isDefault;
}
