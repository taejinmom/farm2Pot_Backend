package com.farm2pot.user.mapper;

import com.farm2pot.user.controller.dto.UserDto;
import com.farm2pot.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T21:47:42+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        if ( arg0.getLoginId() != null ) {
            user.loginId( arg0.getLoginId() );
        }
        if ( arg0.getEmail() != null ) {
            user.email( arg0.getEmail() );
        }
        if ( arg0.getPassword() != null ) {
            user.password( arg0.getPassword() );
        }
        if ( arg0.getName() != null ) {
            user.name( arg0.getName() );
        }
        if ( arg0.getLoginType() != null ) {
            user.loginType( arg0.getLoginType() );
        }
        if ( arg0.getPhoneNo() != null ) {
            user.phoneNo( arg0.getPhoneNo() );
        }
        if ( arg0.getBirthDay() != null ) {
            user.birthDay( arg0.getBirthDay() );
        }
        user.status( arg0.getStatus() );
        if ( arg0.getGender() != null ) {
            user.gender( arg0.getGender() );
        }
        if ( arg0.getNickName() != null ) {
            user.nickName( arg0.getNickName() );
        }
        List<String> list = arg0.getRoles();
        if ( list != null ) {
            user.roles( new ArrayList<String>( list ) );
        }

        User userResult = user.build();

        afterUpdate( arg0, userResult );

        return userResult;
    }

    @Override
    public UserDto toDto(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        if ( arg0.getId() != null ) {
            userDto.id( arg0.getId() );
        }
        if ( arg0.getLoginId() != null ) {
            userDto.loginId( arg0.getLoginId() );
        }
        if ( arg0.getEmail() != null ) {
            userDto.email( arg0.getEmail() );
        }
        if ( arg0.getPassword() != null ) {
            userDto.password( arg0.getPassword() );
        }
        if ( arg0.getName() != null ) {
            userDto.name( arg0.getName() );
        }
        if ( arg0.getLoginType() != null ) {
            userDto.loginType( arg0.getLoginType() );
        }
        if ( arg0.getPhoneNo() != null ) {
            userDto.phoneNo( arg0.getPhoneNo() );
        }
        if ( arg0.getBirthDay() != null ) {
            userDto.birthDay( arg0.getBirthDay() );
        }
        userDto.status( arg0.getStatus() );
        if ( arg0.getGender() != null ) {
            userDto.gender( arg0.getGender() );
        }
        if ( arg0.getNickName() != null ) {
            userDto.nickName( arg0.getNickName() );
        }
        List<String> list = arg0.getRoles();
        if ( list != null ) {
            userDto.roles( new ArrayList<String>( list ) );
        }

        return userDto.build();
    }

    @Override
    public void updateEntityFromDto(UserDto dto, User entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getLoginId() != null ) {
            entity.setLoginId( dto.getLoginId() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getLoginType() != null ) {
            entity.setLoginType( dto.getLoginType() );
        }
        if ( dto.getPhoneNo() != null ) {
            entity.setPhoneNo( dto.getPhoneNo() );
        }
        if ( dto.getBirthDay() != null ) {
            entity.setBirthDay( dto.getBirthDay() );
        }
        entity.setStatus( dto.getStatus() );
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getNickName() != null ) {
            entity.setNickName( dto.getNickName() );
        }

        afterUpdate( dto, entity );
    }
}
