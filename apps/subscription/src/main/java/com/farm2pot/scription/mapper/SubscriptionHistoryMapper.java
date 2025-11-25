package com.farm2pot.scription.mapper;

import com.farm2pot.common.config.MapStructConfig;
import com.farm2pot.common.mapper.BaseMapper;
import com.farm2pot.scription.entity.SubscriptionHistory;
import com.farm2pot.scription.service.dto.SubscriptionHistoryResponse;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface SubscriptionHistoryMapper extends BaseMapper<SubscriptionHistory, SubscriptionHistoryResponse> {

}
