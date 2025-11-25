package com.farm2pot.user.mapper;

import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
import com.farm2pot.user.controller.dto.UserResponse;
import com.farm2pot.user.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * packageName    : com.farm2pot.auth.mapper
 * author         : TAEJIN
 * date           : 2025-10-04
 * description    :
 */
@Mapper(config = MapStructConfig.class)
public interface UserMapper extends BaseMapper<User, UserResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserResponse dto, @MappingTarget User entity);

    @AfterMapping
    default void afterMapping(@MappingTarget User.UserBuilder userBuilder, CreateUserRequest dto) {
        // role 수동 매핑
        if (dto.role() != null) {
            userBuilder.role(dto.role());
        }
    }
}
