package com.naeddoco.nsmwspring.model.subscriptionInfoProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("subscriptionInfoProductService")
public class SubscriptionInfoProductServiceImpl implements SubscriptionInfoProductService {
	
	@Autowired
	private SubscriptionInfoProductDAO subscriptionInfoProductDAO;
	
	@Override
	public boolean delete(SubscriptionInfoProductDTO subscriptionInfoProductDTO) {
		
		return subscriptionInfoProductDAO.delete(subscriptionInfoProductDTO);
		
	}

}
