package com.naeddoco.nsmwspring.model.subscriptionPolicyModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionPolicy")
@Slf4j
public class SubscriptionPolicyDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 모든 구독 정책 정보를 가져오는 쿼리
	private static final String SELECTALL = "SELECT LOWER_LIMIT_MONTH, UPPER_LIMIT_MONTH, DISCOUNT_RATE FROM SUBSCRIPTION_POLICY";
	
	// 특정 조건에 맞는 정책 정보를 가져오는 쿼리
	private static final String SELECTONE = "SELECT " +
											"LOWER_LIMIT_MONTH, " +
											"UPPER_LIMIT_MONTH, " +
											"DISCOUNT_RATE " +
											"FROM SUBSCRIPTION_POLICY " +
											"WHERE LOWER_LIMIT_MONTH <= ? AND ? <= UPPER_LIMIT_MONTH;";
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<SubscriptionPolicyDTO> selectAll(SubscriptionPolicyDTO subscriptionPolicyDTO) {

		log.debug("selectAll 진입");

		try {

			return jdbcTemplate.query(SELECTALL, new SelectAllRowMapper());

		} catch (Exception e) {

			log.debug("selectCartDatas 예외 발생");

			return null;
		}
		
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public SubscriptionPolicyDTO selectOne(SubscriptionPolicyDTO subscriptionPolicyDTO) {
		
		log.debug("selectOne 진입");

		if(subscriptionPolicyDTO.getSearchCondition().equals("insertSubscriptionData")) {
			
			log.debug("insertSubscriptionData 진입");
		
			try {
				
				return jdbcTemplate.queryForObject(SELECTONE, new SelectOneRowMapper());
				
			} catch (Exception e) {
				
				log.debug("insertSubscriptionData 예외처리");
				
				return null;
				
			}
			
		}
		
		log.debug("selectOne 실패");
		
		return null;
		
	}
	

}

class SelectAllRowMapper implements RowMapper<SubscriptionPolicyDTO> {

	@Override
	public SubscriptionPolicyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SubscriptionPolicyDTO subscriptionPolicyDTO = new SubscriptionPolicyDTO();
		
		subscriptionPolicyDTO.setLowerLimitMonth(rs.getInt("LOWER_LIMIT_MONTH"));
		subscriptionPolicyDTO.setUpperLimitMonth(rs.getInt("UPPER_LIMIT_MONTH"));
		subscriptionPolicyDTO.setDiscountRate(rs.getInt("DISCOUNT_RATE"));
		
		return subscriptionPolicyDTO;
		
	}
	
}

class SelectOneRowMapper implements RowMapper<SubscriptionPolicyDTO> {

	@Override
	public SubscriptionPolicyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SubscriptionPolicyDTO subscriptionPolicyDTO = new SubscriptionPolicyDTO();
		
		subscriptionPolicyDTO.setLowerLimitMonth(rs.getInt("LOWER_LIMIT_MONTH"));
		subscriptionPolicyDTO.setUpperLimitMonth(rs.getInt("UPPER_LIMIT_MONTH"));
		subscriptionPolicyDTO.setDiscountRate(rs.getInt("DISCOUNT_RATE"));
		
		return subscriptionPolicyDTO;
		
	}
	
}