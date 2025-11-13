package com.farm2pot.address.controller;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.entity.Address;
import com.farm2pot.address.service.AddressService;
import com.farm2pot.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.farm2pot.user.controller
 * author         : TAEJIN
 * date           : 2025-10-31
 * description    :
 */

@RestController
@RequestMapping("/api/user/addr")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    /**
     * 사용자의 UserId로 사용자의 배송지 목록 조회
     * @param userId
     * @return
     */
    @GetMapping("/address/{userId}")
    public List<Address> findAllAddress(@PathVariable("userId") Long userId) {
        return addressService.findAllAddressByUserId(userId);
    }


    /**
     * 사용자 배송지 추가
     * @param addressData
     * @return
     */
    @PostMapping("/address")
    public void addUserAddress(@RequestBody AddressData addressData) {
            addressService.addUserAddress(addressData);
    }

    /**
     * 사용자의 배송지 목록 중 특정 배송지 하나를 수정
     * @param addrId
     * @param addressData
     * @return
     */
    @PatchMapping("/address/{addrId}")
    public Address editAddress(@PathVariable("addrId") Long addrId, @RequestBody AddressData addressData) {
        return addressService.editUserAddress(addrId, addressData);
    }

    /**
     * 사용자의 UserId로 사용자의 기본 배송지 조회
     * @param userId
     * @return
     */
    @GetMapping("/address/default/{userId}")
    public User findDefaultAddress(@PathVariable("userId") Long userId) {
        return addressService.getUserWithDefaultAddress(userId);
    }

    /**
     * 사용자의 배송지 목록 중 특정 배송지 하나를 삭제
     * @param addrId
     * @return
     */
    @DeleteMapping("/address/{addrId}")
    public void deleteAddress(@PathVariable("addrId") Long addrId) {
        addressService.deleteUserAddressByUserId(addrId);
    }
}
