package com.farm2pot.scription.mapper;

import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
import com.farm2pot.scription.entity.Subscription;
import com.farm2pot.scription.service.dto.SubscriptionResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SubscriptionMapper extends BaseMapper<Subscription, SubscriptionResponse> {

}
