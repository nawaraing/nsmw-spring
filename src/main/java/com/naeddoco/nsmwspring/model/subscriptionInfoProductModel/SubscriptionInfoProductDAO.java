package com.naeddoco.nsmwspring.model.subscriptionInfoProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionInfoProduct")
@Slf4j
public class SubscriptionInfoProductDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String DELETE = "DELETE FROM SUBSCRIPTION_INFO_PRODUCT WHERE SUBSCRIPTION_INFO_ID = ?"; 
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(SubscriptionInfoProductDTO subscriptionInfoProductDTO) {
		
		log.debug("delete 진입");
		
		if(subscriptionInfoProductDTO.getSearchCondition().equals("deleteSubscriptionData")) {
			
			log.debug("deleteSubscriptionData 진입");
				
			int result = jdbcTemplate.update(DELETE, subscriptionInfoProductDTO.getSubscriptionInfoID());
			
			if(result <= 0) {
				
				log.debug("deleteSubscriptionData 실패");
				
				return false;
				
			}
			
			log.debug("deleteSubscriptionData 성공");
			
			return true;
			
		}
		
		log.debug("delete 실패");
		
		return false;
		
	}
		
}