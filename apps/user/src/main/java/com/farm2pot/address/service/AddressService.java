package com.farm2pot.address.service;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.mapper.AddressMapper;
import com.farm2pot.common.exception.UserErrorCode;
import com.farm2pot.common.exception.UserException;
import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.user.entity.User;
import com.farm2pot.address.repository.AddressRepository;
import com.farm2pot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.farm2pot.user.service
 * author         : TAEJIN
 * date           : 2025-10-31
 * description    :
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper userAddressMapper;

    /**
     * USERADDRESS pk로 배송지 찾기
     * @param id
     * @return
     */
    public Address findUserAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_ADDRESS));
    }

    /**
     * 사용자 ID(pk)로 배송지 목록 찾기
     * @param userId
     * @return
     */
    public List<Address> findAllAddressByUserId(Long userId) {
        return addressRepository.findAllAddressByUserId(userId).orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_ADDRESS));
    }


    /**
     * 사용자 배송지 추가
     * @param addressData
     */
    public void addUserAddress(AddressData addressData) {
        Long userId = addressData.getUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        //DTO에 UserEntity 세팅
        addressData.setUser(user);
        //UserAddress Insert
        addressRepository.save(userAddressMapper.toEntity(addressData));

    }

    /**
     * 사용자ID로 사용자의 배송지 모두 제거
     * @param UserId
     */
    public void deleteUserAddressByUserId(Long UserId) {
        addressRepository.deleteByUserId(UserId);
    }
}
