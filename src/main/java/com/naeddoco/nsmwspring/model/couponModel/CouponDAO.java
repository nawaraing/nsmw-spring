package com.naeddoco.nsmwspring.model.couponModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("couponDAO")
@Slf4j
public class CouponDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL_BY_CATEGORY_NAME = "SELECT C.COUPON_ID, C.COUPON_NAME, C.EXPIRATION_DATE, C.COUPON_TYPE, CA.CATEGORY_NAME, " +
			 												 "IF(C.COUPON_TYPE = 'WON', WC.COUPON_DISCOUNT_AMOUNT, NULL) AS DISCOUNT_AMOUNT, " +
			 												 "IF(C.COUPON_TYPE = 'WON', WC.MIN_ORDER_AMOUNT, NULL) AS MIN_ORDER_AMOUNT, " +
			 												 "IF(C.COUPON_TYPE = 'PERCENTAGE', PC.COUPON_DISCOUNT_RATE, NULL) AS DISCOUNT_RATE, " +
			 												 "IF(C.COUPON_TYPE = 'PERCENTAGE', PC.MAX_DISCOUNT_AMOUNT, NULL) AS MAX_DISCOUNT_AMOUNT " +
			 												 "FROM COUPON C " +
			 												 "INNER JOIN COUPON_CATEGORY CC ON C.COUPON_ID = CC.COUPON_ID " +
			 												 "INNER JOIN CATEGORY CA ON CA.CATEGORY_ID = CC.CATEGORY_ID " +
			 												 "LEFT JOIN WON_COUPON WC ON C.COUPON_ID = WC.COUPON_ID " +
			 												 "LEFT JOIN PERCENTAGE_COUPON PC ON C.COUPON_ID = PC.COUPON_ID " +
			 												 "WHERE CA.CATEGORY_NAME = ?";

	private static final String SELECTONE = "";

	private static final String INSERT = "";

	private static final String UPDATE = "";

	private static final String DELETE = "";
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<CouponDTO> selectAll(CouponDTO couponDTO) {
		
		log.debug("CouponDTO selectAll 진입");
		
		if (couponDTO.getSearchCondition().equals("selectAllCoupon")) {
			
			log.debug("selectAllCoupon 진입");
			
			Object[] args = { couponDTO.getAncCategoryName() };
			
			try {
		
				return jdbcTemplate.query(SELECTALL_BY_CATEGORY_NAME, args, new selectOneCouponRowMapper());
			
			} catch (Exception e) {
				
				log.debug("selectAllCoupon 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.debug("CouponDTO selectAll 실패");
		
		return null;
		
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public CouponDTO selectOne(CouponDTO couponDTO) {
		
		return null;
				
	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(CouponDTO couponDTO) {

		return false;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(CouponDTO couponDTO) {

		return false;

	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(CouponDTO couponDTO) {

		return false;

	}
	
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class CouponRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		return data;
	}

}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class selectOneCouponRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO couponDTO = new CouponDTO();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		couponDTO.setCouponID(rs.getInt("C.COUPON_ID"));
		couponDTO.setCouponName(rs.getString("C.COUPON_NAME"));
		couponDTO.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));
		couponDTO.setCouponType(rs.getString("C.COUPON_TYPE"));
		couponDTO.setAncCategoryName(rs.getString("CA.CATEGORY_NAME"));
		couponDTO.setAncDiscountAmount(rs.getInt("DISCOUNT_AMOUNT"));
		couponDTO.setAncMinOrderAmount(rs.getInt("MIN_ORDER_AMOUNT"));
		couponDTO.setAncDiscountRate(rs.getInt("DISCOUNT_RATE"));
		couponDTO.setAncMaxDiscountAmount(rs.getInt("MAX_DISCOUNT_AMOUNT"));

		return couponDTO;
	}

}