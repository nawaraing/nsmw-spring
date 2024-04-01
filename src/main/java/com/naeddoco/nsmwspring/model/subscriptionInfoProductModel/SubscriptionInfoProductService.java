package com.naeddoco.nsmwspring.model.subscriptionInfoProductModel;

import java.util.List;

public interface SubscriptionInfoProductService {
	
	public List<SubscriptionInfoProductDTO> selectAll(SubscriptionInfoProductDTO subscriptionInfoProductDTO);
	public boolean insert(SubscriptionInfoProductDTO subscriptionInfoProductDTO);
	public boolean delete(SubscriptionInfoProductDTO subscriptionInfoPorudctDTO);

}
