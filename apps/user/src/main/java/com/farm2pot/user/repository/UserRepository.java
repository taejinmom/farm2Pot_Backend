package com.farm2pot.user.repository;

import com.farm2pot.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * packageName    : com.farm2pot.auth
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

}

