package com.farm2pot.user.mapper;

import com.farm2pot.user.controller.dto.EditUserRequest;
import com.farm2pot.user.entity.User;
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
public class EditUserMapperImpl implements EditUserMapper {

    @Override
    public User toEntity(EditUserRequest arg0) {
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

        User userResult = user.build();

        afterUpdate( arg0, userResult );

        return userResult;
    }

    @Override
    public EditUserRequest toDto(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        EditUserRequest.EditUserRequestBuilder editUserRequest = EditUserRequest.builder();

        if ( arg0.getId() != null ) {
            editUserRequest.id( arg0.getId() );
        }
        if ( arg0.getLoginId() != null ) {
            editUserRequest.loginId( arg0.getLoginId() );
        }
        if ( arg0.getPassword() != null ) {
            editUserRequest.password( arg0.getPassword() );
        }
        if ( arg0.getEmail() != null ) {
            editUserRequest.email( arg0.getEmail() );
        }
        if ( arg0.getName() != null ) {
            editUserRequest.name( arg0.getName() );
        }
        if ( arg0.getLoginType() != null ) {
            editUserRequest.loginType( arg0.getLoginType() );
        }
        if ( arg0.getPhoneNo() != null ) {
            editUserRequest.phoneNo( arg0.getPhoneNo() );
        }
        if ( arg0.getBirthDay() != null ) {
            editUserRequest.birthDay( arg0.getBirthDay() );
        }
        editUserRequest.status( arg0.getStatus() );
        if ( arg0.getGender() != null ) {
            editUserRequest.gender( arg0.getGender() );
        }
        if ( arg0.getNickName() != null ) {
            editUserRequest.nickName( arg0.getNickName() );
        }
        List<String> list = arg0.getRoles();
        if ( list != null ) {
            editUserRequest.roles( new ArrayList<String>( list ) );
        }

        return editUserRequest.build();
    }

    @Override
    public void updateEntityFromDto(EditUserRequest dto, User entity) {
        if ( dto == null ) {
            return;
        }

        afterUpdate( dto, entity );
    }
}
