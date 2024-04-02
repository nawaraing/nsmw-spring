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
	
	// COUPON insert시 COUPON_CATEGORY에도 함께 insert
	private static final String INSERT = "INSERT INTO COUPON_CATEGORY "
											+ "(COUPON_ID, CATEGORY_ID) "
											+ "VALUES (?,?)";
	
	private static final String UPDATE = "";
	
	//COUPON_ID로 해당 쿠폰 카테고리 삭제
	//카테고리ID UPDATE시 사용(DELETE 후 INSERT)
	private static final String DELETE = "DELETE FROM COUPON_CATEGORY "
										+ "WHERE COUPON_ID = ?";
	
	//----------------------------------------------------------------------------------------------
	
	public List<CouponCategoryDTO> selectAll(CouponCategoryDTO couponCategoryDTO) {
		
		log.trace("selectAll 진입");
		
		if (couponCategoryDTO.getSearchCondition().equals("selectAllCouponCategory")) {
			log.trace("selectAllCouponCategory 진입");

			try {
				return (List<CouponCategoryDTO>)jdbcTemplate.query(SELECTALL, new CouponCategoryRowMapper());
			}
			catch (Exception e){
				log.error("selectAllCouponCategory 예외/실패");

			}
		}
		
		log.error("selectAll 실패");
		return null;
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
	
		log.trace("insert 진입");
		
		if (couponCategoryDTO.getSearchCondition().equals("insertAdminCouponData")) {
		
			log.trace("insertAdminCouponData 진입");
			
			int result = jdbcTemplate.update(INSERT, couponCategoryDTO.getCouponID(), couponCategoryDTO.getCategoryID());

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

		log.trace("delete 진입");
		
		if (couponCategoryDTO.getSearchCondition().equals("deleteAdminCouponGradeData")) {

			log.trace("deleteAdminCouponGradeData 진입");
			
			int result = jdbcTemplate.update(DELETE, couponCategoryDTO.getCouponID());

			if(result <= 0) {
				
				log.error("deleteAdminCouponGradeData 실패");
				return false;
			}
			
			log.trace("delete 성공");
			return true;
		}
		
		log.error("delete 실패");
		return false;
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