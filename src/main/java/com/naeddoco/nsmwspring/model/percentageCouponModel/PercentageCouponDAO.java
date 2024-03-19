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
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
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
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(PercentageCouponDTO percentageCouponDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(PercentageCouponDTO percentageCouponDTO) {
		
//		int result = jdbcTemplate.update(DELETE);
//		if(result <= 0) {
//			log.debug("delete 성공");
			return false;
//		}
//		log.debug("delete 성공");
//		return true;
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