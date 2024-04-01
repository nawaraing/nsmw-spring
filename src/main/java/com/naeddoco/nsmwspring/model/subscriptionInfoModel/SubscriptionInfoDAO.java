package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionInfo")
@Slf4j
public class SubscriptionInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 사용자의 구독 정보를 불러오는 쿼리
	private static final String SELECTALL_SUBSCRIPTION_INFO = "SELECT " +
															  "SI.SUBSCRIPTION_INFO_ID " +
															  "SI.BEGIN_DATE, " +
															  "TOTAL_PRICE, " +
															  "SI.NEXT_PAYMENT_DATE, " +
															  "DATE_ADD(SI.BEGIN_DATE, INTERVAL (SI.SUBSCRIPTION_CLOSING_TIMES - 1) MONTH) AS LAST_PAYMENT_DATE, " +
															  "SI.SUBSCRIPTION_POSTCODE, " +
															  "SI.SUBSCRIPTION_ADDRESS, " +
															  "SI.SUBSCRIPTION_DETAIL_ADDRESS " +
															  "FROM SUBSCRIPTION_INFO SI " +
															  "LEFT JOIN (SELECT SIP.SUBSCRIPTION_INFO_ID, SUM(SIP.PURCHASE_PRICE) * SUM(SIP.QUANTITY) AS TOTAL_PRICE FROM SUBSCRIPTION_INFO_PRODUCT SIP GROUP BY SIP.SUBSCRIPTION_INFO_ID) AS TOTAL_PRICE_TABLE ON SI.SUBSCRIPTION_INFO_ID = TOTAL_PRICE_TABLE.SUBSCRIPTION_INFO_ID " +
															  "WHERE MEMBER_ID = ?";

	// 가장 높은 PK값을 가져오는 쿼리
	private static final String SELECTONE_MAX_PK = "SELECT MAX(SUBSCRIPTION_INFO_ID) AS MAX_PK FROM SUBSCRIPTION_INFO;";
	
	// 사용자의 구독 정보를 추가하는 쿼리
	private static final String INSERT_SUBSCRIPTION_INFO = "INSERT INTO SUBSCRIPTION_INFO (" +
														   "MEMBER_ID, " +
														   "BEGIN_DATE, " +
														   "SUBSCRIPTION_TIMES, " +
														   "NEXT_PAYMENT_DATE, " +
														   "SUBSCRIPTION_POSTCODE, " +
														   "SUBSCRIPTION_ADDRESS, " +
														   "SUBSCRIPTION_DETAIL_ADDRESS, " +
														   "SUBSCRIPTION_CLOSING_TIMES" +
														   ") VALUES (?, ?, 1, DATE_ADD(BEGIN_DATE, INTERVAL (1) MONTH), ?, ?, ?, ?)";
	
	// PK값으로 배송 주소를 수정하는 쿼리
	private static final String UPDATE_ADDRESS = "UPDATE SUBSCRIPTION_INFO " +
												 "SET SUBSCRIPTION_POSTCODE = ?, SUBSCRIPTION_ADDRESS = ?, SUBSCRIPTION_DETAIL_ADDRESS = ? " +
												 "WHERE SUBSCRIPTION_INFO_ID = ?";
	
	private static final String DELETE = "DELETE FROM SUBSCRIPTION_INFO WHERE SUBSCRIPTION_INFO_ID = ?"; 

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<SubscriptionInfoDTO> selectAll(SubscriptionInfoDTO subscriptionInfoDTO) {

		log.debug("selectAll 진입");

		if (subscriptionInfoDTO.getSearchCondition().equals("selectSubscriptionDatas")) {

			log.debug("selectSubscriptionDatas 진입");
			
			Object[] args = { subscriptionInfoDTO.getMemberID() };

			try {

				return jdbcTemplate.query(SELECTALL_SUBSCRIPTION_INFO, args, new selectAllSubscriptionInfoRowMapper());

			} catch (Exception e) {

				log.debug("selectSubscriptionDatas 예외 발생");

				return null;

			}

		} 

		log.debug("selectAll 실패");

		return null;

	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public SubscriptionInfoDTO selectOne(SubscriptionInfoDTO subscriptionInfoDTO) {
		
		log.debug("selectOne 진입");

		if(subscriptionInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");
		
			try {
				
				return jdbcTemplate.queryForObject(SELECTONE_MAX_PK, new selectMaxPKRowMapper());
				
			} catch (Exception e) {
				
				log.debug("insertSubscriptionData 예외처리");
				
				return null;
				
			}
			
		}
		
		log.debug("selectOne 실패");
		
		return null;
		
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public boolean insert(SubscriptionInfoDTO subscriptionInfoDTO) {

		log.debug("insert 진입");

		int result = 0;

		if (subscriptionInfoDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");

			try {
			
				result = jdbcTemplate.update(INSERT_SUBSCRIPTION_INFO, 
											 subscriptionInfoDTO.getMemberID(),
											 subscriptionInfoDTO.getBeginDate(),
											 subscriptionInfoDTO.getSubscriptionPostCode(),
											 subscriptionInfoDTO.getSubscriptionAddress(),
											 subscriptionInfoDTO.getSubscriptionDetailAddress());

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

	public boolean delete(SubscriptionInfoDTO subscriptionInfoDTO) {
		
		log.debug("delete 진입");
		
		if(subscriptionInfoDTO.getSearchCondition().equals("deleteSubscriptionData")) {
			
			log.debug("deleteSubscriptionData 진입");
				
			int result = jdbcTemplate.update(DELETE, subscriptionInfoDTO.getSubscriptionInfoID());
			
			if(result <= 0) {
				
				log.debug("eleteSubscriptionData 실패");
				
				return false;
				
			}
			
			log.debug("eleteSubscriptionData 성공");
			
			return true;
			
		}
		
		log.debug("delete 실패");
		
		return false;
		
	}
	
}

@Slf4j
class selectAllSubscriptionInfoRowMapper implements RowMapper<SubscriptionInfoDTO> {

	@Override
	public SubscriptionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectAllSubscriptionInfoRowMapper 진입");

		SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();

		subscriptionInfoDTO.setSubscriptionInfoID(rs.getInt("SUBSCRIPTION_INFO_ID"));
		subscriptionInfoDTO.setBeginDate(rs.getTimestamp("SI.BEGIN_DATE"));
		subscriptionInfoDTO.setAncTotalPrice(rs.getInt("TOTAL_PRICE"));
		subscriptionInfoDTO.setNextPaymentDate(rs.getTimestamp("SI.NEXT_PAYMENT_DATE"));
		subscriptionInfoDTO.setAncLastPaymentDate(rs.getTimestamp("LAST_PAYMENT_DATE"));
		subscriptionInfoDTO.setSubscriptionPostCode(rs.getInt("SI.SUBSCRIPTION_POSTCODE"));
		subscriptionInfoDTO.setSubscriptionAddress(rs.getString("SI.SUBSCRIPTION_ADDRESS"));
		subscriptionInfoDTO.setSubscriptionAddress(rs.getString("SI.SUBSCRIPTION_DETAIL_ADDRESS"));
		
		log.debug("getProductDetailRowMapper 완료");

		return subscriptionInfoDTO;

	}

}

@Slf4j
class selectMaxPKRowMapper implements RowMapper<SubscriptionInfoDTO> {

	@Override
	public SubscriptionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectMaxPKRowMapper 진입");

		SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();

		subscriptionInfoDTO.setMaxPK(rs.getInt("MAX_PK"));
		
		log.debug("selectMaxPKRowMapper 완료");

		return subscriptionInfoDTO;

	}

}