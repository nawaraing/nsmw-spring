package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

public interface SubscriptionInfoService {
	
	public boolean update(SubscriptionInfoDTO subscriptionInfoDTO);
	public boolean delete(SubscriptionInfoDTO subscriptionInfoDTO);

}
