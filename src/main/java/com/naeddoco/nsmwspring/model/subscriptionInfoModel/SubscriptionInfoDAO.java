package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.monthlySalesStatsModel.MonthlySalesStatsDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionInfo")
@Slf4j
public class SubscriptionInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// PK값으로 배송 주소를 수정하는 쿼리
	private static final String UPDATE_ADDRESS = "UPDATE SUBSCRIPTION_INFO " +
												 "SET SUBSCRIPTION_POSTCODE = ?, SUBSCRIPTION_ADDRESS = ?, SUBSCRIPTION_DETAIL_ADDRESS = ? " +
												 "WHERE SUBSCRIPTION_INFO_ID = ?";

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(SubscriptionInfoDTO subscriptionInfoDTO) {

		log.debug("update 진입");

		if (subscriptionInfoDTO.getSearchCondition().equals("updateSubscriptionShippingData")) {

			log.debug("updateSubscriptionShippingData 진입");
			
			Object[] args = { subscriptionInfoDTO.getSubscriptionPostCode(), 
				        	  subscriptionInfoDTO.getSubscriptionAddress(), 
				        	  subscriptionInfoDTO.getSubscriptionDetailAddress(),
				        	  subscriptionInfoDTO.getSubscriptionInfoID() };

			int result = jdbcTemplate.update(UPDATE_ADDRESS, args);

			if (result <= 0) {

				log.error("updateSubscriptionShippingData 실패");

				return false;

			}

			log.debug("updateSubscriptionShippingData 성공");

			return true;

		}

		log.error("update 실패");
		
		return false;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
}