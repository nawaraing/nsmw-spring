package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

@Service("subscriptionInfoService")
public class SubscriptionInfoServiceImpl implements SubscriptionInfoService {
	
	@Autowired
	private SubscriptionInfoDAO subscriptionInfoDAO;
	
	@Override
	public boolean update(SubscriptionInfoDTO subscriptionInfoDTO) {
		
		return subscriptionInfoDAO.update(subscriptionInfoDTO);
		
	}
	
	@Override
	public boolean delete(SubscriptionInfoDTO subscriptionInfoDTO) {
		
		return subscriptionInfoDAO.delete(subscriptionInfoDTO);
		
	}

}
