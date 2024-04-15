package com.naeddoco.nsmwspring.model.wonCoupon;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("wonCouponDAO")
@Slf4j
public class WonCouponDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//COUPON INSERT시 같이 INSERT
	private static final String INSERT = "INSERT INTO WON_COUPON "
									+ "(COUPON_ID, COUPON_DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT) "
									+ "VALUES (?,?,?)";
	
	//쿠폰정보 변경시 사용
	private static final String UPDATE = "UPDATE WON_COUPON "
										+ "SET "
											+ "COUPON_DISCOUNT_AMOUNT = ?, "
											+ "MIN_ORDER_AMOUNT = ? "
										+ "WHERE WON_COUPON_ID = ?";
	

/*-----------------------------------[ insert ] ------------------------------------------------------------------------------------------------------------*/

	public boolean insert(WonCouponDTO wonCouponDTO) {
		log.trace("insert 진입");

		if (wonCouponDTO.getSearchCondition().equals("insertAdminCouponData")) {

			log.trace("insertAdminCouponData 진입");

			int result = jdbcTemplate.update(INSERT, wonCouponDTO.getCouponID(), 
													wonCouponDTO.getCouponDiscountAmount(), 
													wonCouponDTO.getMinOrderAmount() );
		
			if(result <= 0) {
				log.error("insertAdminCouponData 실패");
				return false;
			
			}
			
			log.trace("insertAdminCouponData 성공");
			return true;
		
		}
		
		log.error("insert 실패");
		return false;
	}
	
/*-----------------------------------[ update ] ------------------------------------------------------------------------------------------------------------*/

	public boolean update(WonCouponDTO wonCouponDTO) {
		log.trace("update 진입");

		if ( wonCouponDTO.getSearchCondition().equals("updateAdminCouponGradeData")) {

			log.trace("updateAdminCouponGradeData 진입");

			int result = jdbcTemplate.update(UPDATE, wonCouponDTO.getCouponDiscountAmount(), 
													 wonCouponDTO.getMinOrderAmount(),
													 wonCouponDTO.getWonCouponID());
		
			if(result <= 0) {
				log.error("updateAdminCouponGradeData 실패");
				return false;
			
			}
			
			log.trace("updateAdminCouponGradeData 성공");
			return true;
		
		}
		
		log.error("update 실패");
		return false;
	}
}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

@Slf4j
class WonCouponRowMapper implements RowMapper<WonCouponDTO> {
	@Override
	public WonCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		WonCouponDTO data = new WonCouponDTO();
		
		data.setWonCouponID(rs.getInt("WON_COUPON_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCouponDiscountAmount(rs.getInt("COUPON_DISCOUNT_AMOUNT"));
		data.setMinOrderAmount(rs.getInt("MIN_ORDER_AMOUNT"));
			
		log.debug(Integer.toString(rs.getInt("WON_COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_DISCOUNT_AMOUNT")));
		log.debug(Integer.toString(rs.getInt("MIN_ORDER_AMOUNT")));
		
		return data;
	}
	
}
