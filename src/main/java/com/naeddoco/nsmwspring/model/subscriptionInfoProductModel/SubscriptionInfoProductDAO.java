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
	
	// 구독 상품 정보를 모두 가져오는 쿼리
	private static final String SELECTALL_SUBSCRIPTION_PRODUCT = "SELECT P.PRODUCT_NAME, SIP.QUANTITY, SIP.PURCHASE_PRICE " +
																 "FROM SUBSCRIPTION_INFO_PRODUCT SIP " +
																 "INNER JOIN PRODUCT P ON SIP.PRODUCT_ID = P.PRODUCT_ID " +
																 "WHERE SIP.SUBSCRIPTION_INFO_ID = ?";
	
	// 구독 상품 정보를 추가하는 쿼리
	private static final String INSERT_SUBSCRIPTION_PRODUCT = "INSERT INTO SUBSCRIPTION_INFO_PRODUCT (" +
															  "SUBSCRIPTION_INFO_ID, " +
															  "PRODUCT_ID, " +
															  "QUANTITY, " +
															  "PURCHASE_PRICE" +
															  ") VALUES (?, ?, ?, ?)";	
	
	// 구독 상품 정보를 삭제하는 쿼리
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
	
	public boolean insert(SubscriptionInfoProductDTO subscriptionInfoProductDTO) {

		log.debug("insert 진입");

		int result = 0;

		if (subscriptionInfoProductDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");

			try {
			
				result = jdbcTemplate.update(INSERT_SUBSCRIPTION_PRODUCT, 
											 subscriptionInfoProductDTO.getSubscriptionInfoID(),
											 subscriptionInfoProductDTO.getProductID(),
											 subscriptionInfoProductDTO.getQuantity(),
											 subscriptionInfoProductDTO.getPurchasePrice());

			} catch (Exception e) {
			
				log.debug("insertSubscriptionData 예외 발생");

				return false;

			}
			
		}

		if (result <= 0) {

			return false;

		}
		
		log.debug("insert 처리 실패");

		return true;

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

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class selectAllSubscriptionProductRowMapper implements RowMapper<SubscriptionInfoProductDTO> {

	@Override
	public SubscriptionInfoProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectAllSubscriptionProductRowMapper 진입");

		SubscriptionInfoProductDTO subscriptionInfoProductDTO = new SubscriptionInfoProductDTO();

		subscriptionInfoProductDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		subscriptionInfoProductDTO.setQuantity(rs.getInt("SIP.QUANTITY"));
		subscriptionInfoProductDTO.setPurchasePrice(rs.getInt("SIP.PURCHASE_PRICE"));
		
		log.debug("selectAllSubscriptionProductRowMapper 완료");

		return subscriptionInfoProductDTO;

	}

}