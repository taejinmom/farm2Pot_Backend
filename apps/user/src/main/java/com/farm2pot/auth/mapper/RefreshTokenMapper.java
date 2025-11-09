package com.farm2pot.auth.mapper;

import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.entity.RefreshToken;
import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * packageName    : com.farm2pot.auth.refreshToken.mapper
 * author         : TAEJIN
 * date           : 2025-10-04
 * description    :
 */

@Mapper(config = MapStructConfig.class)
public interface RefreshTokenMapper extends BaseMapper<RefreshToken, TokenRefresh> {}
