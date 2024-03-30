package com.naeddoco.nsmwspring.model.subscriptionInfoProductModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionInfoProduct")
@Slf4j
public class SubscriptionInfoProductDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECTALL_SUBSCRIPTION_PRODUCT = "SELECT P.PRODUCT_NAME, SIP.QUANTITY " +
																 "FROM SUBSCRIPTION_INFO_PRODUCT SIP " +
																 "INNER JOIN PRODUCT P ON SIP.PRODUCT_ID = P.PRODUCT_ID " +
																 "WHERE SIP.SUBSCRIPTION_INFO_ID = ?";

	private static final String DELETE = "DELETE FROM SUBSCRIPTION_INFO_PRODUCT WHERE SUBSCRIPTION_INFO_ID = ?"; 
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<SubscriptionInfoProductDTO> selectAll(SubscriptionInfoProductDTO subscriptionInfoProductDTO) {

		log.debug("selectAll 진입");

		if (subscriptionInfoProductDTO.getSearchCondition().equals("selectSubscriptionDatas")) {

			log.debug("selectSubscriptionDatas 진입");
			
			Object[] args = { subscriptionInfoProductDTO.getSubscriptionInfoID() };

			try {

				return jdbcTemplate.query(SELECTALL_SUBSCRIPTION_PRODUCT, args, new selectAllSubscriptionProductRowMapper());

			} catch (Exception e) {

				log.debug("selectSubscriptionDatas 예외 발생");

				return null;

			}

		} 

		log.debug("selectAll 실패");

		return null;

	}
	
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

@Slf4j
class selectAllSubscriptionProductRowMapper implements RowMapper<SubscriptionInfoProductDTO> {

	@Override
	public SubscriptionInfoProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectAllSubscriptionProductRowMapper 진입");

		SubscriptionInfoProductDTO subscriptionInfoProductDTO = new SubscriptionInfoProductDTO();

		subscriptionInfoProductDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		subscriptionInfoProductDTO.setQuantity(rs.getInt("SIP.QUANTITY "));
		
		log.debug("selectAllSubscriptionProductRowMapper 완료");

		return subscriptionInfoProductDTO;

	}

}