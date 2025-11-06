package com.farm2pot.address.mapper;

import com.farm2pot.address.entity.Address;
import com.farm2pot.address.controller.dto.AddressData;
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
public interface AddressMapper extends BaseMapper<Address, AddressData> {

    @Override
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(AddressData dto, @MappingTarget Address entity);
}
