package com.farm2pot.address.mapper;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.service.dto.DefaultAddressResponse;
import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
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
public interface DefaultAddressMapper extends BaseMapper<Address, DefaultAddressResponse> {

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.loginId", target = "loginId")
    @Mapping(source = "default", target = "isDefault")
    DefaultAddressResponse toDto(Address entity);

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(DefaultAddressResponse dto, @MappingTarget Address entity);
}
