package com.farm2pot.address.mapper;

import com.farm2pot.address.controller.dto.AddressData;
import com.farm2pot.address.entity.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T21:47:42+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toEntity(AddressData arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        if ( arg0.user() != null ) {
            address.user( arg0.user() );
        }
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
    public AddressData toDto(Address arg0) {
        if ( arg0 == null ) {
            return null;
        }

        AddressData.AddressDataBuilder addressData = AddressData.builder();

        if ( arg0.getId() != null ) {
            addressData.id( arg0.getId() );
        }
        if ( arg0.getUser() != null ) {
            addressData.user( arg0.getUser() );
        }
        if ( arg0.getRecipientName() != null ) {
            addressData.recipientName( arg0.getRecipientName() );
        }
        if ( arg0.getPhoneNumber() != null ) {
            addressData.phoneNumber( arg0.getPhoneNumber() );
        }
        if ( arg0.getPostalCode() != null ) {
            addressData.postalCode( arg0.getPostalCode() );
        }
        if ( arg0.getAddressLine1() != null ) {
            addressData.addressLine1( arg0.getAddressLine1() );
        }
        if ( arg0.getAddressLine2() != null ) {
            addressData.addressLine2( arg0.getAddressLine2() );
        }
        if ( arg0.getCreatedAt() != null ) {
            addressData.createdAt( arg0.getCreatedAt() );
        }
        if ( arg0.getUpdatedAt() != null ) {
            addressData.updatedAt( arg0.getUpdatedAt() );
        }

        return addressData.build();
    }

    @Override
    public void updateEntityFromDto(AddressData dto, Address entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.user() != null ) {
            entity.setUser( dto.user() );
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
}
