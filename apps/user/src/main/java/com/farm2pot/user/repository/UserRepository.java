package com.farm2pot.user.repository;

import com.farm2pot.user.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
/**
 * packageName    : com.farm2pot.auth
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.id = :id and u.status = 1")
    Optional<User> findById(Long id);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    @Query("""
        SELECT u 
        FROM User u 
        LEFT JOIN FETCH u.addresses a 
        WHERE u.id = :userId AND a.isDefault = true
    """)
    Optional<User> findUserWithAddresses(@Param("userId") Long userId);
}

