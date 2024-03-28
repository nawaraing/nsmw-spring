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
    // 관리자페이지_등급 쿠폰 조회
	// 1개의 쿠폰에 카테고리가 여러개라면 CATEGORY_NAME에 ;로 구분되어 조회
	// ex)뇌;뼈/치아;간;
	private static final String SELECTALL_COUPON_INFO ="SELECT DISTINCT "
															+ "C.COUPON_NAME, "
															+ "C.EXPIRATION_DATE AS COUPON_EXPIRATION_DATE, "
															+ "CONCAT("
																+ "(SELECT GROUP_CONCAT(CATEGORY_NAME SEPARATOR ';') "
																+ "FROM COUPON_CATEGORY CC "
																+ "JOIN CATEGORY C ON CC.CATEGORY_ID = C.CATEGORY_ID "
																+ "WHERE CC.COUPON_ID = ?),"
																+ "';') AS CATEGORY_NAME, "
															+ "CASE "
																+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN 'PERCENTAGE' "
																+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN 'WON' "
															+ "END AS COUPON_TYPE, "
															+ "CASE "
																+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
															+ "END AS DISCOUNT, "
															+ "CASE "
																+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
															+ "END AS AMOUNT_LIMIT, "
															+ "PC.DEPLOY_CYCLE AS DISTRIBUTION_CYCLE, "
															+ "PC.DEPLOY_BASE AS DISTRIBUTION_CRITERIA, "
															+ "G.GRADE_NAME "
														+ "FROM "
															+ "COUPON C "
														+ "LEFT JOIN "
															+ "PERCENTAGE_COUPON P ON C.COUPON_ID = P.COUPON_ID "
														+ "LEFT JOIN "
															+ "WON_COUPON W ON C.COUPON_ID = W.COUPON_ID "
														+ "LEFT JOIN "
															+ "COUPON_CATEGORY CC ON C.COUPON_ID = CC.COUPON_ID "
														+ "LEFT JOIN "
															+ "CATEGORY CAT ON CC.CATEGORY_ID = CAT.CATEGORY_ID "
														+ "LEFT JOIN "
															+ "PROVISION_GRADE_COUPON PC ON C.COUPON_ID = PC.COUPON_ID "
														+ "LEFT JOIN "
															+ "GRADE G ON PC.GRADE_ID = G.GRADE_ID "
														+ "WHERE C.COUPON_ID = ?";
	
	// 쿠폰 insert 시 생성된 couponID 가져옴
	private static final String SELECTONE_LAST_ID = "SELECT LAST_INSERT_ID()";

	// 관리자 페이지에서 쿠폰추가시 사용
	private static final String INSERT = "INSERT INTO COUPON (COUPON_NAME, DISTRIBUTE_DATE, EXPIRATION_DATE, COUPON_TYPE) "
											+ "VALUES (?, ?, CONCAT(?, ' 23:59:59'),?)";

	private static final String UPDATE = "UPDATE COUPON "
										+ "SET COUPON_NAME = ?,"
											+ "EXPIRATION_DATE = ?,"
											+ "COUPON_TYPE = ?"
										+ "WHERE COUPON_ID = ?";

	// COUPON삭제 기능
	// 등급쿠폰과 연관된 테이블에 FK ON DELETE CASCADE 설정이 되어있어
	// COUPON테이블 데이터 삭제시 함께 삭제됨
	private static final String DELETE = "DELETE FROM COUPON"
										+ "WHERE COUPON_ID = ?";
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<CouponDTO> selectAll(CouponDTO couponDTO) {
		
		log.trace("CouponDTO selectAll 진입");
		
		if (couponDTO.getSearchCondition().equals("selectAllCoupon")) {
			
			log.trace("selectAllCoupon 진입");
			
			Object[] args = { couponDTO.getAncCategoryName() };
			
			try {
		
				return jdbcTemplate.query(SELECTALL_BY_CATEGORY_NAME, args, new selectOneCouponRowMapper());
			
			} catch (Exception e) {
				
				log.error("selectAllCoupon 예외/실패");
				
				return null;
				
			}
			
		}
		
		log.error("CouponDTO selectAll 실패");
		
		return null;
		
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public CouponDTO selectOne(CouponDTO couponDTO) {

		log.trace("selectOne 처리 진입");
		
		if(couponDTO.getSearchCondition().equals("selectLastId")) {
			
			log.trace("selectLastId 처리 진입");
			
			try {

				return jdbcTemplate.queryForObject(SELECTONE_LAST_ID, new selectOneCouponIdRowMapper());

			} catch (Exception e) {
				
				log.error("selectLastId 예외/실패");

				return null;

			}
			
		}
		
		log.error("selectOne 처리 실패");
		
		return null;
				
	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(CouponDTO couponDTO) {

		log.trace("insert 진입");
		if(couponDTO.getSearchCondition().equals("insertAdminCouponGradeData")) {
			
			log.trace("insertAdminCouponGradeData 진입");
			int result = jdbcTemplate.update(INSERT, couponDTO.getCouponName(), 
					couponDTO.getDistributeDate(), 
					couponDTO.getExpirationDate(), 
					couponDTO.getCouponType());

			if(result <= 0) {
				log.error("insertAdminCouponGradeData 실패");
				return false;
			}

			log.trace("insertAdminCouponGradeData 성공");
			return true;

		}

		log.error("insert 실패");
		return false;
	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(CouponDTO couponDTO) {

		log.trace("update 진입");
		if(couponDTO.getSearchCondition().equals("updateAdminCouponGradeData")) {
			
			log.trace("updateAdminCouponGradeData 진입");
			int result = jdbcTemplate.update(UPDATE, couponDTO.getCouponName(), 
													couponDTO.getExpirationDate(), 
													couponDTO.getCouponType(),
													couponDTO.getCouponID());

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
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(CouponDTO couponDTO) {
		log.trace("delete 진입");

		if (couponDTO.getSearchCondition().equals("deleteAdminCouponGradeData")) {

			log.trace("deleteAdminCouponGradeData 진입");

			int result = jdbcTemplate.update(DELETE, couponDTO.getCouponID());

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
@Slf4j
class selectOneCouponIdRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO couponDTO = new CouponDTO();

		couponDTO.setCouponID(rs.getInt("COUPON_ID"));

		return couponDTO;
	}

}
