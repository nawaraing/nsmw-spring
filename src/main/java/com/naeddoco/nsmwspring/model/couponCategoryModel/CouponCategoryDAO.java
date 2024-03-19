package com.naeddoco.nsmwspring.model.couponCategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("couponCategoryDAO")
@Slf4j
public class CouponCategoryDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	public List<CouponCategoryDTO> selectAll(CouponCategoryDTO couponCategoryDTO) {
		log.debug("selectAll start");
		return (List<CouponCategoryDTO>)jdbcTemplate.query(SELECTALL, new CouponCategoryRowMapper());
	}

	
	public CouponCategoryDTO selectOne(CouponCategoryDTO couponCategoryDTO) {

//		Object[] args = { couponCategoryDTO.getCouponCategoryID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new CouponCategoryRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(CouponCategoryDTO couponCategoryDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(CouponCategoryDTO couponCategoryDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(CouponCategoryDTO couponCategoryDTO) {
		
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
class CouponCategoryRowMapper implements RowMapper<CouponCategoryDTO> {
	@Override
	public CouponCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponCategoryDTO data = new CouponCategoryDTO();
		
		data.setCouponCategoryID(rs.getInt("COUPON_CATEGORY_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCategoryID(rs.getInt("CATEGORY_ID"));
			
		return data;
	}
	
}