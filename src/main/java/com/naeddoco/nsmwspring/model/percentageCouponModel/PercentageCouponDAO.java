package com.naeddoco.nsmwspring.model.percentageCouponModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("percentageCouponDAO")
@Slf4j
public class PercentageCouponDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	
	//관리자페이지에서 쿠폰정보 등록시 사용
	//COUPON insert시 같이 insert
	private static final String INSERT = "INSERT INTO PERCENTAGE_COUPON "
										+ "(COUPON_ID, COUPON_DISCOUNT_RATE, MAX_DISCOUNT_AMOUNT) "
										+ "VALUES (?,?,?)";
	
	//쿠폰정보 변경
	private static final String UPDATE = "UPDATE PERCENTAGE_COUPON "
										+ "SET "
											+ "COUPON_ID = ?, "
											+ "COUPON_DISCOUNT_RATE = ?, "
											+ "MAX_DISCOUNT_AMOUNT = ? "
										+ "WHERE PERCENTAGE_COUPON_ID = ?";
	
	private static final String DELETE = "";
	
	
	public List<PercentageCouponDTO> selectAll(PercentageCouponDTO monthlySalesStatsDTO) {
		log.debug("selectAll start");
		return (List<PercentageCouponDTO>)jdbcTemplate.query(SELECTALL, new PercentageCouponRowMapper());
	}

	
	public PercentageCouponDTO selectOne(PercentageCouponDTO percentageCouponDTO) {

//		Object[] args = { percentageCouponDTO.getPercentageCouponID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new PercentageCouponRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(PercentageCouponDTO percentageCouponDTO) {
	
		log.trace("insert 진입");

		if (percentageCouponDTO.getSearchCondition().equals("insertAdminCouponData")) {

			log.trace("insertAdminCouponData 진입");

			int result = jdbcTemplate.update(INSERT, percentageCouponDTO.getCouponID(), 
													percentageCouponDTO.getCouponDiscountRate(), 
													percentageCouponDTO.getMaxDiscountAmount());
		
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
	

	
	public boolean update(PercentageCouponDTO percentageCouponDTO) {

		log.trace("update 진입");

		if (percentageCouponDTO.getSearchCondition().equals("updateAdminCouponGradeData")) {

			log.trace("updateAdminCouponGradeData 진입");

			int result = jdbcTemplate.update(UPDATE, percentageCouponDTO.getCouponID(),
													percentageCouponDTO.getCouponDiscountRate(), 
													percentageCouponDTO.getMaxDiscountAmount(),
													percentageCouponDTO.getPercentageCouponID());
		
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

	
	public boolean delete(PercentageCouponDTO percentageCouponDTO) {
		
			return false;

	}	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class PercentageCouponRowMapper implements RowMapper<PercentageCouponDTO> {
	@Override
	public PercentageCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		PercentageCouponDTO data = new PercentageCouponDTO();
		
		data.setPercentageCouponID(rs.getInt("PERCENTAGE_COUPON_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCouponDiscountRate(rs.getInt("COUPON_DISCOUNT_RATE"));
		data.setMaxDiscountAmount(rs.getInt("MAX_DISCOUNT_AMOUNT"));
			
		log.debug(Integer.toString(rs.getInt("PERCENTAGE_COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_DISCOUNT_RATE")));
		log.debug(Integer.toString(rs.getInt("MAX_DISCOUNT_AMOUNT")));
		
		return data;
	}
	
}