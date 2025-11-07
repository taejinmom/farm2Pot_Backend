package com.farm2pot.address.service;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.mapper.AddressMapper;
import com.farm2pot.common.exception.UserErrorCode;
import com.farm2pot.common.exception.UserException;
import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.user.entity.User;
import com.farm2pot.address.repository.AddressRepository;
import com.farm2pot.user.repository.UserRepository;
import com.farm2pot.user.service.dto.UserWithDefaultAddressDto;
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
    private final AddressMapper addressMapper;

    /**
     * USERADDRESS pk로 배송지 찾기
     * @param id
     * @return
     */
    public Address findUserAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new UserException(UserErrorCode.ADDRESS_NOT_FOUND));
    }

    /**
     * 사용자 ID(pk)로 배송지 목록 찾기
     * @param userId
     * @return
     */
    public List<Address> findAllAddressByUserId(Long userId) {
        return addressRepository.findAllAddressByUserId(userId).orElseThrow(() -> new UserException(UserErrorCode.ADDRESS_NOT_FOUND));
    }


    /**
     * 사용자 배송지 추가
     * @param addressData
     */
    public void addUserAddress(AddressData addressData) {
        Long userId = addressData.userId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        //DTO에 UserEntity 세팅
        AddressData updatedData = addressData.toBuilder()
                .user(user)
                .build();
        //Address Insert
        addressRepository.save(addressMapper.toEntity(updatedData));

    }

    /**
     * 사용자ID로 사용자의 배송지 모두 제거
     * @param UserId
     */
    public void deleteUserAddressByUserId(Long UserId) {
        addressRepository.deleteByUserId(UserId);
    }

    /**
     * userId로 user + 기본 주소 함께 조회
     */
    public UserWithDefaultAddressDto getUserWithDefaultAddress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Address address = user.getDefaultAddress();

        return new UserWithDefaultAddressDto(
                user.getId(),
                user.getLoginId(),
                address.getRecipientName(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getPhoneNumber(),
                address
        );
    }
}
