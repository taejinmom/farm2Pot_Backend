package com.farm2pot.address.mapper;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.service.dto.DefaultAddressResponse;
import com.farm2pot.user.entity.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T02:05:05+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class DefaultAddressMapperImpl implements DefaultAddressMapper {

    @Override
    public Address toEntity(DefaultAddressResponse arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        if ( arg0.recipientName() != null ) {
            address.recipientName( arg0.recipientName() );
        }
        if ( arg0.phoneNumber() != null ) {
            address.phoneNumber( arg0.phoneNumber() );
        }
        if ( arg0.postalCode() != null ) {
            address.postalCode( arg0.postalCode() );
        }
        if ( arg0.addressLine1() != null ) {
            address.addressLine1( arg0.addressLine1() );
        }
        if ( arg0.addressLine2() != null ) {
            address.addressLine2( arg0.addressLine2() );
        }
        address.isDefault( arg0.isDefault() );
        if ( arg0.createdAt() != null ) {
            address.createdAt( arg0.createdAt() );
        }
        if ( arg0.updatedAt() != null ) {
            address.updatedAt( arg0.updatedAt() );
        }

        return address.build();
    }

    @Override
    public DefaultAddressResponse toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        Long userId = null;
        String loginId = null;
        boolean isDefault = false;
        Long id = null;
        String recipientName = null;
        String phoneNumber = null;
        String postalCode = null;
        String addressLine1 = null;
        String addressLine2 = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        Long id1 = entityUserId( entity );
        if ( id1 != null ) {
            userId = id1;
        }
        String loginId1 = entityUserLoginId( entity );
        if ( loginId1 != null ) {
            loginId = loginId1;
        }
        isDefault = entity.isDefault();
        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getRecipientName() != null ) {
            recipientName = entity.getRecipientName();
        }
        if ( entity.getPhoneNumber() != null ) {
            phoneNumber = entity.getPhoneNumber();
        }
        if ( entity.getPostalCode() != null ) {
            postalCode = entity.getPostalCode();
        }
        if ( entity.getAddressLine1() != null ) {
            addressLine1 = entity.getAddressLine1();
        }
        if ( entity.getAddressLine2() != null ) {
            addressLine2 = entity.getAddressLine2();
        }
        if ( entity.getCreatedAt() != null ) {
            createdAt = entity.getCreatedAt();
        }
        if ( entity.getUpdatedAt() != null ) {
            updatedAt = entity.getUpdatedAt();
        }

        DefaultAddressResponse defaultAddressResponse = new DefaultAddressResponse( id, userId, loginId, recipientName, phoneNumber, postalCode, addressLine1, addressLine2, isDefault, createdAt, updatedAt );

        return defaultAddressResponse;
    }

    @Override
    public void updateEntityFromDto(DefaultAddressResponse dto, Address entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.recipientName() != null ) {
            entity.setRecipientName( dto.recipientName() );
        }
        if ( dto.phoneNumber() != null ) {
            entity.setPhoneNumber( dto.phoneNumber() );
        }
        if ( dto.postalCode() != null ) {
            entity.setPostalCode( dto.postalCode() );
        }
        if ( dto.addressLine1() != null ) {
            entity.setAddressLine1( dto.addressLine1() );
        }
        if ( dto.addressLine2() != null ) {
            entity.setAddressLine2( dto.addressLine2() );
        }
        if ( dto.createdAt() != null ) {
            entity.setCreatedAt( dto.createdAt() );
        }
        if ( dto.updatedAt() != null ) {
            entity.setUpdatedAt( dto.updatedAt() );
        }
    }

    private Long entityUserId(Address address) {
        User user = address.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String entityUserLoginId(Address address) {
        User user = address.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getLoginId();
    }
}
