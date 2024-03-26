package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("subscriptionInfoService")
public class SubscriptionInfoServiceImpl implements SubscriptionInfoService {
	
	@Autowired
	private SubscriptionInfoDAO subscriptionInfoDAO;
	
	@Override
	public boolean update(SubscriptionInfoDTO subscriptionInfoDTO) {
		
		return subscriptionInfoDAO.update(subscriptionInfoDTO);
		
	}

}
