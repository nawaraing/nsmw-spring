package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import java.util.List;

public interface SubscriptionInfoService {
	
	public List<SubscriptionInfoDTO> selectAll(SubscriptionInfoDTO subscriptionInfoDTO);
	public SubscriptionInfoDTO selectOne(SubscriptionInfoDTO subscriptionInfoDTO);
	public boolean insert(SubscriptionInfoDTO subscriptionInfoDTO);
	public boolean update(SubscriptionInfoDTO subscriptionInfoDTO);
	public boolean delete(SubscriptionInfoDTO subscriptionInfoDTO);

}
