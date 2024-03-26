package com.naeddoco.nsmwspring.model.subscriptionPolicyModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("subscriptionPolicyService")
public class SubscriptionPolicyServiceImpl implements SubscriptionPolicyService {
	
	@Autowired
	private SubscriptionPolicyDAO subscriptionPolicyDAO;
	
	@Override
	public List<SubscriptionPolicyDTO> selectAll(SubscriptionPolicyDTO subscriptionPolicyDTO) {
		
		return subscriptionPolicyDAO.selectAll(subscriptionPolicyDTO);
		
	}

}
