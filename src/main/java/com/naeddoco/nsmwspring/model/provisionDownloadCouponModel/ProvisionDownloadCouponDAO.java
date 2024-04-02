package com.naeddoco.nsmwspring.model.provisionDownloadCouponModel;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("provisionDownloadCouponDAO")
@Slf4j
public class ProvisionDownloadCouponDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 관리자페이지_다운로드 쿠폰 목록 조회
	// 등록일 오름차순 정렬
	private static final String SELECTALL = "SELECT DISTINCT "
											+ "PDC.PROVISION_DOWNLOAD_COUPON_ID, "
											+ "C.COUPON_NAME, C.CREATE_DATE, "
											+ "C.DISTRIBUTE_DATE , "
											+ "C.EXPIRATION_DATE, "
											+ "CONCAT((SELECT GROUP_CONCAT(CA.CATEGORY_NAME SEPARATOR ';') "
												+ "FROM CATEGORY CA "
												+ "JOIN COUPON_CATEGORY CC "
												+ "ON CA.CATEGORY_ID = CC.CATEGORY_ID "
												+ "WHERE C.COUPON_ID = CC.COUPON_ID)) AS CATEGORY_NAME, "
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
												+ "C.CREATE_DATE ASC ";
	
	private static final String SELECTONE = "";

	private static final String INSERT = "INSERT INTO PROVISION_DOWNLOAD_COUPON "
										+ "(COUPON_ID, IMAGE_ID, DEPLOY_DEADLINE, DEPLOY_STATUS) "
										+ "VALUES (?,?,?,?)";

	private static final String UPDATE = "";
	
	private static final String DELETE = "";

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ProvisionDownloadCouponDTO> selectAll(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {

		log.trace("selectAll 진입");

		if (provisionDownloadCouponDTO.getSearchCondition().equals("selectAllAdminCouponDownloadDatas")) {

			log.trace("selectAllAdminCouponDownloadDatas 진입 ");

			try {

				return (List<ProvisionDownloadCouponDTO>) jdbcTemplate.query(SELECTALL, new ProvisionDownloadCouponRowMapper());

			} catch (Exception e) {

				log.error("selectAllAdminCouponDownloadDatas 예외/실패 ");

				return null;

			}

		}

		log.error("selectAll 실패");

		return null;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ProvisionDownloadCouponDTO selectOne(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		
//		log.debug("[로그] 처리 진입");
//		
//		if(provisionGradeCouponDTO.getSearchCondition().equals("")) {
//			
//			log.debug("[로그]처리 진입");
//			
//			Object[] args = { provisionDownloadCouponDTO.getProvisionDownloadCouponID() };
//
//			try {
//
//				return jdbcTemplate.queryForObject(SELECTONE, args, new ProvisionDownloadCouponRowMapper());
//
//			} catch (Exception e) {
//				
//				log.debug("[로그] 예외 발생");
//
//				return null;
//
//			}
//			
//		}
//		
//		log.debug("[로그] 처리 실패");
		
		return null;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {

		log.trace("insert 처리 진입");

		int result = 0;

		if (provisionDownloadCouponDTO.getSearchCondition().equals("insertAdminCouponGradeData")) {
			log.trace("insertAdminCouponGradeData 처리 진입");
			
			result = jdbcTemplate.update(INSERT, provisionDownloadCouponDTO.getCouponID(),
												 provisionDownloadCouponDTO.getImageID(), 
												 provisionDownloadCouponDTO.getDeployDeadline(), 
												 provisionDownloadCouponDTO.getDeployStatus());
			
			if (result <= 0) {

				log.error("insertAdminCouponGradeData 실패");
				return false;

			}

			log.trace("insertAdminCouponGradeData 성공");
			return true;
		}
		
		log.error("insert 실패");
		return false;
	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {

//		log.trace("update 진입");
//
//		if (provisionDownloadCouponDTO.getSearchCondition().equals("updateAdminCouponGradeData")) {
//
//			log.trace("updateAdminCouponGradeData 진입");
//
//			int result = jdbcTemplate.update(UPDATE);
//
//			if(result <= 0) {
//				log.error("updateAdminCouponGradeData 실패");
//				return false;
//
//			}
//
//			log.trace("updateAdminCouponGradeData 성공");
//			return true;
//
//		}

		log.error("update 실패");
		return false;
	}
	
	
	public boolean delete(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
//		int result = jdbcTemplate.update(UPDATE);
//
//		if (result <= 0) {

			return false;

//		}
//
//		return true;

	}


}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class ProvisionDownloadCouponRowMapper implements RowMapper<ProvisionDownloadCouponDTO> {

	@Override
	public ProvisionDownloadCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.trace("ProvisionDownloadCouponRowMapper 처리 진입");

		ProvisionDownloadCouponDTO data = new ProvisionDownloadCouponDTO();

		data.setProvisionDownloadCouponID(rs.getInt("PDC.PROVISION_DOWNLOAD_COUPON_ID"));
		data.setAncCouponName(rs.getString("C.COUPON_NAME"));
		data.setAncCreateDate(rs.getTimestamp("C.CREATE_DATE"));
		data.setAncDistributeDate(rs.getTimestamp("C.DISTRIBUTE_DATE"));
		data.setAncExpirationDate(rs.getTimestamp("C.EXPIRATION_DATE"));
		data.setAncCategoryName(rs.getString("CATEGORY_NAME"));
		data.setAncCouponType(rs.getString("C.COUPON_TYPE"));
		data.setAncDiscount(rs.getInt("DISCOUNT"));
		data.setAncAmount(rs.getInt("AMOUNT_LIMIT"));
		data.setDeployStatus(rs.getString("PDC.DEPLOY_STATUS"));
		data.setDeployDeadline(rs.getTimestamp("PDC.DEPLOY_DEADLINE"));
		data.setAncImageID(rs.getInt("I.IMAGE_ID"));
		data.setAncImagePath(rs.getString("I.IMAGE_PATH"));

		log.trace("ProvisionDownloadCouponRowMapper 처리 완료");

		return data;

	}

}
