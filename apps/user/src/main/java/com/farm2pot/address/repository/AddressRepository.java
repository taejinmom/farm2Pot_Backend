package com.farm2pot.address.repository;

import com.farm2pot.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.farm2pot.auth
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(Long Id);
    Optional<List<Address>> findAllAddressByUserId(Long userId);
    Optional<String> deleteByUserId(Long userId);
    @Query("""
        SELECT a 
        FROM Address a 
        WHERE a.user.id = :userId 
          AND a.isDefault = true
    """)
    Optional<Address> findByUserIdAndIsDefaultTrue(Long userId);
}

