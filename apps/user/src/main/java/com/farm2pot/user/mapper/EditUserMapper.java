package com.farm2pot.user.mapper;

import com.farm2pot.auth.controller.dto.CreateUserRequest;
import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
import com.farm2pot.user.controller.dto.EditUserRequest;
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
public interface EditUserMapper extends BaseMapper<User, EditUserRequest> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EditUserRequest dto, @MappingTarget User entity);

    @AfterMapping
    default void afterMapping(@MappingTarget User.UserBuilder userBuilder, CreateUserRequest dto) {
        // role 수동 매핑
        if (dto.role() != null) {
            userBuilder.role(dto.role());
        }
    }
}
