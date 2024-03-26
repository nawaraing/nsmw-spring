package com.naeddoco.nsmwspring.model.subscriptionPolicyModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("subscriptionPolicy")
@Slf4j
public class SubscriptionPolicyDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "SELECT LOWER_LIMIT_MONTH, UPPER_LIMIT_MONTH, DISCOUNT_RATE FROM SUBSCRIPTION_POLICY";

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