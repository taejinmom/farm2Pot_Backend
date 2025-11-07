package com.farm2pot.address.controller;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.entity.Address;
import com.farm2pot.common.response.ResponseMessage;
import com.farm2pot.address.service.AddressService;
import com.farm2pot.user.service.dto.UserWithDefaultAddressDto;
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
    public ResponseMessage<List<Address>> findAllAddress(@PathVariable("userId") Long userId) {
        return ResponseMessage.success("Select UserAddress By UserId", addressService.findAllAddressByUserId(userId));
    }


    /**
     * 사용자 배송지 추가
     * @param addressData
     * @return
     */
    @PostMapping("/address")
    public ResponseMessage<String> addUserAddress(@RequestBody AddressData addressData) {
        try{
            addressService.addUserAddress(addressData);
            return ResponseMessage.success("Insert UserAddress.... Success");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseMessage.fail("Insert UserAddress.... Failure");
        }
    }

    /**
     * 사용자의 배송지 목록 중 특정 배송지 하나를 수정
     * @param addrId
     * @param addressData
     * @return
     */
    @PutMapping("/address/{addrId}")
    public ResponseMessage<Address> editAddress(@PathVariable("addrId") Long addrId, @RequestBody AddressData addressData) {
        return ResponseMessage.success("Select UserAddress By UserId", addressService.findUserAddressById(addrId));
    }

    /**
     * 사용자의 UserId로 사용자의 기본 배송지 조회
     * @param userId
     * @return
     */
    @GetMapping("/address/default/{userId}")
    public ResponseMessage<UserWithDefaultAddressDto> findDefaultAddress(@PathVariable("userId") Long userId) {
        return ResponseMessage.success("Select Default Address By UserId", addressService.getUserWithDefaultAddress(userId));
    }

    /**
     * 사용자의 배송지 목록 중 특정 배송지 하나를 삭제
     * @param addrId
     * @return
     */
    @DeleteMapping("/address/delete/uid/{addrId}")
    public ResponseMessage<String> deleteAddress(@PathVariable("addrId") Long addrId) {
        addressService.deleteUserAddressByUserId(addrId);
        return ResponseMessage.success("Delete UserAddress By AddressId");
    }
}
