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
	// 기본정렬은 등록일-오름차순
	private static final String SELECTALL_COUPON_INFO_GRADE ="SELECT DISTINCT "
																+ "C.COUPON_ID"
																+ "C.CREATE_DATE"
																+ "C.COUPON_NAME, "
																+ "C.EXPIRATION_DATE, "
																+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
																	+ "FROM CATEGORY CA "
																	+ "JOIN COUPON_CATEGORY CC "
																	+ "ON CA.CATEGORY_ID = CC.CATEGORY_ID "
																	+ "WHERE C.COUPON_ID = CC.COUPON_ID)"
																	+ ") AS CATEGORY_NAME, "
																+ "C.COUPON_TYPE,"
																+ "CASE "
																	+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																	+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
																+ "END AS DISCOUNT, "
																+ "CASE "
																	+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																	+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
																+ "END AS AMOUNT_LIMIT, "
																+ "PC.DEPLOY_CYCLE, "
																+ "PC.DEPLOY_BASE,"
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
																+ "JOIN "
																	+ "PROVISION_GRADE_COUPON PC ON C.COUPON_ID = PC.COUPON_ID "
																+ "LEFT JOIN"
																	+ "GRADE G ON PC.GRADE_ID = G.GRADE_ID "
																+ "ORDER BY C.CREATE_DATE ASC";

	//등급쿠폰 문자열 검색 또는 정렬
	private static final String SELECTALL_SEARCH_AND_SORT_BY_GRADE = "SELECT DISTINCT "
																	+ "C.COUPON_ID"
																	+ "C.CREATE_DATE, "
																	+ "C.COUPON_NAME, "
																	+ "C.EXPIRATION_DATE, "
																	+ "CONCAT((SELECT GROUP_CONCAT(CATEGORY_NAME SEPARATOR ';') "
																		+ "FROM COUPON_CATEGORY CC "
																		+ "JOIN CATEGORY C ON CC.CATEGORY_ID = C.CATEGORY_ID "
																		+ "WHERE CC.COUPON_ID = C.COUPON_ID), ';') AS CATEGORY_NAME,"
																	+ "C.COUPON_TYPE, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
																	+ "END AS DISCOUNT, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
																	+ "END AS AMOUNT_LIMIT, "
																		+ "PC.DEPLOY_CYCLE, "
																		+ "PC.DEPLOY_BASE, "
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
																	+ "WHERE "
																		+ "1=1 "
																		+ "AND IF(? IS NOT NULL, C.COUPON_NAME LIKE CONCAT('%', ?, '%'), 1) "
																	+ "ORDER BY ";	
	
	// 관리자페이지_전체 발송 쿠폰 조회
	// 1개의 쿠폰에 카테고리가 여러개라면 CATEGORY_NAME에 ;로 구분되어 조회
	// ex)뇌;뼈/치아;간;
	// 기본정렬은 등록일-오름차순
	private static final String SELECTALL_COUPON_INFO_BATCH = "SELECT DISTINCT "
																	+ "C.COUPON_ID , C.COUPON_NAME, C.CREATE_DATE, C.DISTRIBUTE_DATE , C.EXPIRATION_DATE, "
																	+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
																		+ "FROM CATEGORY CA "
																		+ "JOIN COUPON_CATEGORY CC "
																		+ "ON CA.CATEGORY_ID = CC.CATEGORY_ID "
																		+ "WHERE C.COUPON_ID = CC.COUPON_ID)) "
																		+ "AS CATEGORY_NAME, "
																	+ "C.COUPON_TYPE, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
																	+ "END AS DISCOUNT, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
																	+ "END AS AMOUNT_LIMIT, "
																	+ "PBC.DEPLOY_STATUS "
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
																	+ "JOIN "
																		+ "PROVISION_BATCH_COUPON PBC ON PBC.COUPON_ID = C.COUPON_ID "
																	+ "ORDER BY "
																		+ "C.CREATE_DATE ASC";

	//전체 발송 쿠폰 문자열 검색 또는 정렬
	private static final String SELECTALL_SEARCH_AND_SORT_BY_BATCH = "SELECT DISTINCT "
																				+ "C.COUPON_ID , C.COUPON_NAME, C.CREATE_DATE, C.DISTRIBUTE_DATE , C.EXPIRATION_DATE, "
																				+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
																					+ "FROM CATEGORY CA "
																					+ "JOIN COUPON_CATEGORY CC "
																					+ "ON CA.CATEGORY_ID = CC.CATEGORY_ID "
																					+ "WHERE C.COUPON_ID = CC.COUPON_ID)) "
																					+ "AS CATEGORY_NAME, "
																				+ "C.COUPON_TYPE, "
																				+ "CASE "
																					+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																					+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
																				+ "END AS DISCOUNT, "
																				+ "CASE "
																					+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																					+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
																				+ "END AS AMOUNT_LIMIT, "
																				+ "PBC.DEPLOY_STATUS "
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
																				+ "JOIN "
																					+ "PROVISION_BATCH_COUPON PBC ON PBC.COUPON_ID = C.COUPON_ID"
																				+ "WHERE "
																					+ "1=1 "
																					+ "AND IF(? IS NOT NULL, C.COUPON_NAME LIKE CONCAT('%', ?, '%'), 1) "
																			+ "ORDER BY ";
	
	// 관리자페이지_사용자 다운로드 쿠폰 조회
	// 1개의 쿠폰에 카테고리가 여러개라면 CATEGORY_NAME에 ;로 구분되어 조회
	// ex)뇌;뼈/치아;간;
	// 기본정렬은 등록일-오름차순
	private static final String SELECTALL_COUPON_INFO_DOWNLOAD = "SELECT DISTINCT "
																	+ "PDC.PROVISION_DOWNLOAD_COUPON_ID, "
																	+ "C.COUPON_NAME, C.CREATE_DATE, C.DISTRIBUTE_DATE , "
																	+ "C.EXPIRATION_DATE, "
																	+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
																		+ "	FROM CATEGORY CA "
																		+ "	JOIN COUPON_CATEGORY CC "
																		+ "	ON CA.CATEGORY_ID = CC.CATEGORY_ID "
																		+ "	WHERE C.COUPON_ID = CC.COUPON_ID)) AS CATEGORY_NAME, "
																	+ "C.COUPON_TYPE, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.COUPON_DISCOUNT_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.COUPON_DISCOUNT_RATE "
																	+ "END AS DISCOUNT, "
																	+ "CASE "
																		+ "WHEN W.WON_COUPON_ID IS NOT NULL THEN W.MIN_ORDER_AMOUNT "
																		+ "WHEN P.PERCENTAGE_COUPON_ID IS NOT NULL THEN P.MAX_DISCOUNT_AMOUNT "
																	+ "END AS AMOUNT_LIMIT, "
																	+ "PDC.DEPLOY_STATUS, "
																	+ "PDC.DEPLOY_DEADLINE, "
																	+ "I.IMAGE_ID, "
																	+ "I.IMAGE_PATH "
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
																	+ "JOIN "
																		+ "PROVISION_DOWNLOAD_COUPON PDC ON PDC.COUPON_ID = C.COUPON_ID "
																	+ "LEFT JOIN "
																		+ "IMAGE I ON I.IMAGE_ID = PDC.IMAGE_ID "
																	+ "ORDER BY "
																		+ "C.CREATE_DATE ASC";		
	
	
	
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

				return (List<CouponDTO>)jdbcTemplate.query(SELECTALL_BY_CATEGORY_NAME, args, new selectCategoryNameCouponRowMapper());

			} catch (Exception e) {

				log.error("selectAllCoupon 예외/실패" + e.getLocalizedMessage());

				return null;

			}

		}
		else if (couponDTO.getSearchCondition().equals("selectAllGradeCouponInfo")) {

			log.trace("selectAllCouponInfo 진입");

			try {

				return (List<CouponDTO>)jdbcTemplate.query(SELECTALL_COUPON_INFO_GRADE, new selectAllGradeCouponInfoRowMapper());

			} catch (Exception e) {

				log.error("selectAllCouponInfo 예외/실패");

				return null;

			}

		}
		else if (couponDTO.getSearchCondition().equals("selectAllSearchAndSortGrade")) {

			log.trace("selectAllSearchAndSort 진입");

			Object[] args = {couponDTO.getAncSearchKeyword(),
							 couponDTO.getAncSearchKeyword()};

			// 쿠폰명 검색은 기본 적용
			// 검색 키워드가 없더라도 정상작동되는 쿼리문
			String query = SELECTALL_SEARCH_AND_SORT_BY_GRADE;

			String sqlQuery = null;

			// 만료일순 정렬시
			if(couponDTO.getAncSortColumnName() == "expirationDate") {  
				
				log.trace("expirationDate 진입");
				sqlQuery = query + " C.EXPIRATION_DATE";

			}
			// 할인순 정렬시
			else if (couponDTO.getAncSortColumnName() == "discount") {

				log.trace("discount 진입");
				sqlQuery = query + " COUPON_TYPE, DISCOUNT DESC";

			}
			// 정렬값 없을시 기본 쿠폰생성일로 정렬
			else {

				log.trace("createDate 진입");
				sqlQuery = query + " C.CREATE_DATE";

			}


			try {

				return (List<CouponDTO>)jdbcTemplate.query(sqlQuery, args, new selectAllGradeCouponInfoRowMapper());

			} catch (Exception e) {

				log.error("selectAllSearchAndSort 예외/실패");

				return null;

			}

		}
		else if (couponDTO.getSearchCondition().equals("selectAllBatchCouponInfo")) {

			log.trace("selectAllBatchCouponInfo 진입");

			try {

				return (List<CouponDTO>)jdbcTemplate.query(SELECTALL_COUPON_INFO_BATCH, new selectAllBatchCouponInfoRowMapper());

			} catch (Exception e) {

				log.error("selectAllBatchCouponInfo 예외/실패");

				return null;

			}

		}
		else if (couponDTO.getSearchCondition().equals("selectAllSearchAndSortBatch")) {

			log.trace("selectAllSearchAndSortBatch 진입");

			Object[] args = {couponDTO.getAncSearchKeyword(),
					couponDTO.getAncSearchKeyword()};

			// 쿠폰명 검색은 기본 적용
			// 검색 키워드가 없더라도 정상작동되는 쿼리문
			String query = SELECTALL_SEARCH_AND_SORT_BY_BATCH;

			String sqlQuery = null;

			// 배포현황순 정렬시(WILL/STOP/DONE순 정렬)
			if(couponDTO.getAncSortColumnName() == "deployStatus") {  

				log.trace("deployStatus 진입");
				sqlQuery = query + " PBC.DEPLOY_STATUS DESC";

			}
			// 할인순 정렬시
			else if (couponDTO.getAncSortColumnName() == "discount") {

				log.trace("discount 진입");
				sqlQuery = query + " COUPON_TYPE, DISCOUNT DESC";

			}
			// 정렬값 없을시 기본 쿠폰생성일로 정렬
			else {

				log.trace("createDate 진입");
				sqlQuery = query + " C.CREATE_DATE";

			}

			try {

				return (List<CouponDTO>)jdbcTemplate.query(sqlQuery, args, new selectAllBatchCouponInfoRowMapper());

			} catch (Exception e) {

				log.error("selectAllSearchAndSortBatch 예외/실패");

				return null;

			}

		}
		else if (couponDTO.getSearchCondition().equals("selectAllDownloadCouponInfo")) {

			log.trace("selectAllDownloadCouponInfo 진입 ");

			try {

				return (List<CouponDTO>) jdbcTemplate.query(SELECTALL_COUPON_INFO_DOWNLOAD, new selectAllDownloadCouponInfoRowMapper());

			} catch (Exception e) {

				log.error("selectAllDownloadCouponInfo 예외/실패 ");

				return null;

			}
			
		}
		

		log.error("selectAll 실패");

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
		if(couponDTO.getSearchCondition().equals("insertAdminCouponData")) {
			
			log.trace("insertAdminCouponData 진입");
			int result = jdbcTemplate.update(INSERT, couponDTO.getCouponName(), 
					couponDTO.getDistributeDate(), 
					couponDTO.getExpirationDate(), 
					couponDTO.getCouponType());

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
class selectCategoryNameCouponRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		data.setCouponID(rs.getInt("C.COUPON_ID"));
		data.setCouponName(rs.getString("C.COUPON_NAME"));
		data.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));
		data.setCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncCategoryName(rs.getString("CA.CATEGORY_NAME"));
		data.setAncDiscountAmount(rs.getInt("DISCOUNT_AMOUNT"));
		data.setAncMinOrderAmount(rs.getInt("MIN_ORDER_AMOUNT"));
		data.setAncDiscountRate(rs.getInt("DISCOUNT_RATE"));
		data.setAncMaxDiscountAmount(rs.getInt("MAX_DISCOUNT_AMOUNT"));

		return data;
	}

}

@Slf4j
class selectOneCouponIdRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		data.setCouponID(rs.getInt("COUPON_ID"));

		return data;
	}

}

@Slf4j
class selectAllGradeCouponInfoRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		data.setCouponID(rs.getInt("C.COUPON_ID"));
		data.setCreateDate(rs.getTimestamp("C.CREATE_DATE"));
		data.setCouponName(rs.getString("C.COUPON_NAME"));
		data.setExpirationDate(rs.getTimestamp("C.EXPIRATION_DATE"));
		data.setAncCategoryName(rs.getString("CATEGORY_NAME"));
		data.setCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncDiscount(rs.getInt("DISCOUNT"));
		data.setAncAmount(rs.getInt("AMOUNT_LIMIT"));
		data.setAncDeployCycle(rs.getString("PC.DEPLOY_CYCLE"));
		data.setAncDeployBase(rs.getString("PC.DEPLOY_BASE"));
		data.setAncCategoryName(rs.getString("G.GRADE_NAME"));
		
		return data;
	}

}


@Slf4j
class selectAllBatchCouponInfoRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		data.setCouponID(rs.getInt("C.COUPON_ID"));
		data.setCouponName(rs.getString("C.COUPON_NAME"));
		data.setCreateDate(rs.getTimestamp("C.CREATE_DATE"));
		data.setDistributeDate(rs.getTimestamp("C.DISTRIBUTE_DATE"));
		data.setExpirationDate(rs.getTimestamp("C.EXPIRATION_DATE"));
		data.setAncCategoryName(rs.getString("CATEGORY_NAME"));
		data.setCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncDiscount(rs.getInt("DISCOUNT"));
		data.setAncAmount(rs.getInt("AMOUNT_LIMIT"));
		data.setAncDeployStatus(rs.getString("PBC.DEPLOY_STATUS"));
		
		return data;
	}

}

@Slf4j
class selectAllDownloadCouponInfoRowMapper implements RowMapper<CouponDTO> {
	
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CouponDTO data = new CouponDTO();

		data.setAncProvisionDownloadCouponID(rs.getInt("PDC.PROVISION_DOWNLOAD_COUPON_ID"));
		data.setCouponName(rs.getString("C.COUPON_NAME"));
		data.setCreateDate(rs.getTimestamp("C.CREATE_DATE"));
		data.setDistributeDate(rs.getTimestamp("C.DISTRIBUTE_DATE"));
		data.setExpirationDate(rs.getTimestamp("C.EXPIRATION_DATE"));
		data.setAncCategoryName(rs.getString("CATEGORY_NAME"));
		data.setCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncDiscount(rs.getInt("DISCOUNT"));
		data.setAncAmount(rs.getInt("AMOUNT_LIMIT"));
		data.setAncDeployStatus(rs.getString("PDC.DEPLOY_STATUS"));
		data.setAncDeployDeadline(rs.getTimestamp("PDC.DEPLOY_DEADLINE"));
		data.setAncImageID(rs.getInt("I.IMAGE_ID"));
		data.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		
		return data;
	}

}
