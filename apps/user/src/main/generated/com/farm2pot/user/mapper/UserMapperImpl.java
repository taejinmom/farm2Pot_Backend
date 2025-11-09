package com.farm2pot.user.mapper;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.service.dto.DefaultAddressResponse;
import com.farm2pot.user.controller.dto.UserResponse;
import com.farm2pot.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T02:05:05+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserResponse arg0) {
        if ( arg0 == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        if ( arg0.loginId() != null ) {
            user.loginId( arg0.loginId() );
        }
        if ( arg0.email() != null ) {
            user.email( arg0.email() );
        }
        if ( arg0.password() != null ) {
            user.password( arg0.password() );
        }
        if ( arg0.name() != null ) {
            user.name( arg0.name() );
        }
        if ( arg0.loginType() != null ) {
            user.loginType( arg0.loginType() );
        }
        if ( arg0.phoneNo() != null ) {
            user.phoneNo( arg0.phoneNo() );
        }
        if ( arg0.birthDay() != null ) {
            user.birthDay( arg0.birthDay() );
        }
        user.status( arg0.status() );
        if ( arg0.gender() != null ) {
            user.gender( arg0.gender() );
        }
        if ( arg0.nickName() != null ) {
            user.nickName( arg0.nickName() );
        }
        List<String> list = arg0.roles();
        if ( list != null ) {
            user.roles( new ArrayList<String>( list ) );
        }
        List<Address> list1 = defaultAddressResponseListToAddressList( arg0.addresses() );
        if ( list1 != null ) {
            user.addresses( list1 );
        }

        User userResult = user.build();

        afterUpdate( arg0, userResult );

        return userResult;
    }

    @Override
    public UserResponse toDto(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        if ( arg0.getId() != null ) {
            userResponse.id( arg0.getId() );
        }
        if ( arg0.getLoginId() != null ) {
            userResponse.loginId( arg0.getLoginId() );
        }
        if ( arg0.getEmail() != null ) {
            userResponse.email( arg0.getEmail() );
        }
        if ( arg0.getPassword() != null ) {
            userResponse.password( arg0.getPassword() );
        }
        if ( arg0.getName() != null ) {
            userResponse.name( arg0.getName() );
        }
        if ( arg0.getLoginType() != null ) {
            userResponse.loginType( arg0.getLoginType() );
        }
        if ( arg0.getPhoneNo() != null ) {
            userResponse.phoneNo( arg0.getPhoneNo() );
        }
        if ( arg0.getBirthDay() != null ) {
            userResponse.birthDay( arg0.getBirthDay() );
        }
        userResponse.status( arg0.getStatus() );
        if ( arg0.getGender() != null ) {
            userResponse.gender( arg0.getGender() );
        }
        if ( arg0.getNickName() != null ) {
            userResponse.nickName( arg0.getNickName() );
        }
        List<String> list = arg0.getRoles();
        if ( list != null ) {
            userResponse.roles( new ArrayList<String>( list ) );
        }
        List<DefaultAddressResponse> list1 = addressListToDefaultAddressResponseList( arg0.getAddresses() );
        if ( list1 != null ) {
            userResponse.addresses( list1 );
        }

        return userResponse.build();
    }

    @Override
    public void updateEntityFromDto(UserResponse dto, User entity) {
        if ( dto == null ) {
            return;
        }

        if ( entity.getAddresses() != null ) {
            entity.getAddresses().clear();
            List<Address> list = defaultAddressResponseListToAddressList( dto.addresses() );
            if ( list != null ) {
                entity.getAddresses().addAll( list );
            }
        }

        afterUpdate( dto, entity );
    }

    protected Address defaultAddressResponseToAddress(DefaultAddressResponse defaultAddressResponse) {
        if ( defaultAddressResponse == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        if ( defaultAddressResponse.id() != null ) {
            address.id( defaultAddressResponse.id() );
        }
        if ( defaultAddressResponse.recipientName() != null ) {
            address.recipientName( defaultAddressResponse.recipientName() );
        }
        if ( defaultAddressResponse.phoneNumber() != null ) {
            address.phoneNumber( defaultAddressResponse.phoneNumber() );
        }
        if ( defaultAddressResponse.postalCode() != null ) {
            address.postalCode( defaultAddressResponse.postalCode() );
        }
        if ( defaultAddressResponse.addressLine1() != null ) {
            address.addressLine1( defaultAddressResponse.addressLine1() );
        }
        if ( defaultAddressResponse.addressLine2() != null ) {
            address.addressLine2( defaultAddressResponse.addressLine2() );
        }
        address.isDefault( defaultAddressResponse.isDefault() );
        if ( defaultAddressResponse.createdAt() != null ) {
            address.createdAt( defaultAddressResponse.createdAt() );
        }
        if ( defaultAddressResponse.updatedAt() != null ) {
            address.updatedAt( defaultAddressResponse.updatedAt() );
        }

        return address.build();
    }

    protected List<Address> defaultAddressResponseListToAddressList(List<DefaultAddressResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<Address> list1 = new ArrayList<Address>( list.size() );
        for ( DefaultAddressResponse defaultAddressResponse : list ) {
            list1.add( defaultAddressResponseToAddress( defaultAddressResponse ) );
        }

        return list1;
    }

    protected DefaultAddressResponse addressToDefaultAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        Long id = null;
        String recipientName = null;
        String phoneNumber = null;
        String postalCode = null;
        String addressLine1 = null;
        String addressLine2 = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        if ( address.getId() != null ) {
            id = address.getId();
        }
        if ( address.getRecipientName() != null ) {
            recipientName = address.getRecipientName();
        }
        if ( address.getPhoneNumber() != null ) {
            phoneNumber = address.getPhoneNumber();
        }
        if ( address.getPostalCode() != null ) {
            postalCode = address.getPostalCode();
        }
        if ( address.getAddressLine1() != null ) {
            addressLine1 = address.getAddressLine1();
        }
        if ( address.getAddressLine2() != null ) {
            addressLine2 = address.getAddressLine2();
        }
        if ( address.getCreatedAt() != null ) {
            createdAt = address.getCreatedAt();
        }
        if ( address.getUpdatedAt() != null ) {
            updatedAt = address.getUpdatedAt();
        }

        Long userId = null;
        String loginId = null;
        boolean isDefault = false;

        DefaultAddressResponse defaultAddressResponse = new DefaultAddressResponse( id, userId, loginId, recipientName, phoneNumber, postalCode, addressLine1, addressLine2, isDefault, createdAt, updatedAt );

        return defaultAddressResponse;
    }

    protected List<DefaultAddressResponse> addressListToDefaultAddressResponseList(List<Address> list) {
        if ( list == null ) {
            return null;
        }

        List<DefaultAddressResponse> list1 = new ArrayList<DefaultAddressResponse>( list.size() );
        for ( Address address : list ) {
            list1.add( addressToDefaultAddressResponse( address ) );
        }

        return list1;
    }
}
