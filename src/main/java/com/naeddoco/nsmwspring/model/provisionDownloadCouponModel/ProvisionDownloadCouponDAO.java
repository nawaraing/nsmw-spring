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

	// 쿠폰 다운로드 팝업시 보여줄 쿠폰 정보 출력
	// 현재 로그인한 회원의 ID로 회원이 가지고 있지 않은 다운로드 쿠폰, 배포상태가 'DOING'인 쿠폰ID 출력
	private static final String SELECTALL_POPUP_COUPON_MEMBER = "SELECT "
															+ "PDC.PROVISION_DOWNLOAD_COUPON_ID, "
															+ "C.COUPON_ID, "
															+ "I.IMAGE_PATH "
															+ "FROM "
																+ "PROVISION_DOWNLOAD_COUPON PDC "
															+ "JOIN "
																+ "COUPON C ON PDC.COUPON_ID = C.COUPON_ID "
															+ "JOIN "
																+ "IMAGE I ON PDC.IMAGE_ID = I.IMAGE_ID "
															+ "LEFT JOIN "
																+ "MEMBER_COUPON MC ON PDC.COUPON_ID  = MC.COUPON_ID "
															+ "AND MC.MEMBER_ID = ? "
															+ "WHERE MC.COUPON_ID IS NULL "
															+ "AND PDC.DEPLOY_STATUS ='DOING'";
	
	// 비로그인시 보여줄 모든 다운로드 쿠폰 정보 출력
	private static final String SELECTALL_POPUP_COUPON = "SELECT "
														+ "PDC.PROVISION_DOWNLOAD_COUPON_ID, "
														+ "C.COUPON_ID, "
														+ "I.IMAGE_PATH "
														+ "FROM "
															+ "PROVISION_DOWNLOAD_COUPON PDC "
														+ "JOIN "
															+ "COUPON C ON PDC.COUPON_ID = C.COUPON_ID "
														+ "JOIN "
															+ "IMAGE I ON PDC.IMAGE_ID = I.IMAGE_ID "
														+ "WHERE "
															+ "PDC.DEPLOY_STATUS ='DOING'";
	
	private static final String SELECTONE = "";
	
	// 이미지 INSERT시 생성된 imageID 가져옴
	private static final String SELECTONE_LAST_ID = "SELECT LAST_INSERT_ID() AS IMAGE_ID";

	private static final String INSERT = "INSERT INTO PROVISION_DOWNLOAD_COUPON "
										+ "(COUPON_ID, IMAGE_ID, DEPLOY_DEADLINE, DEPLOY_STATUS) "
										+ "VALUES (?,?,?,?)";
	// 쿠폰 이미지 변경시 IMAGE_ID도 변경
	private static final String UPDATE_IMAGE_ID = "UPDATE PROVISION_DOWNLOAD_COUPON "
										+ "SET IMAGE_ID = ? "
										+ "WHERE PROVISION_DOWNLOAD_COUPON_ID = ?";
	
	// 다운로드쿠폰 정보 변경
	private static final String UPDATE = "UPDATE PROVISION_DOWNLOAD_COUPON "
										+ "SET DEPLOY_DEADLINE = ? "
										+ "WHERE PROVISION_DOWNLOAD_COUPON_ID = ? ";
	
	
	// 배포상태 '중단'으로 변경
	private static final String UPDATE_DEPLOY_STATUS = "UPDATE PROVISION_DOWNLOAD_COUPON "
													 + "SET DEPLOY_STATUS = 'STOP' "
													 + "WHERE PROVISION_DOWNLOAD_COUPON_ID = ?";
	
	private static final String DELETE = "";

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ProvisionDownloadCouponDTO> selectAll(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {

		log.trace("selectAll 진입");

		if (provisionDownloadCouponDTO.getSearchCondition().equals("selectPopupCouponDatas")) {
			
			Object[] args = { provisionDownloadCouponDTO.getAncMemberID() };

			log.trace("selectPopupCouponDatas 진입");

			try {

				return (List<ProvisionDownloadCouponDTO>) jdbcTemplate.query(SELECTALL_POPUP_COUPON, args, new ProvisionDownloadCouponRowMapper());

			} catch (Exception e) {

				log.error("selectPopupCouponDatas 예외/실패 ");

				return null;

			}

		}

		log.error("selectAll 실패");

		return null;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ProvisionDownloadCouponDTO selectOne(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		
		log.trace("selectOne 처리 진입");
		
		if(provisionDownloadCouponDTO.getSearchCondition().equals("selectLastId")) {
			
			log.trace("selectLastId 처리 진입");
			
			try {

				return jdbcTemplate.queryForObject(SELECTONE_LAST_ID, new selectOneImageIdRowMapper());

			} catch (Exception e) {
				
				log.error("에러/예외 발생" + e.getMessage());

				return null;

			}
			
		}
		
		log.debug("[로그] 처리 실패");
		
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

		log.trace("update 진입");

		if (provisionDownloadCouponDTO.getSearchCondition().equals("updateAdminCouponDownloadImageID")) {

			log.trace("updateAdminCouponDownloadImageID 진입");

			int result = jdbcTemplate.update(UPDATE_IMAGE_ID, provisionDownloadCouponDTO.getImageID(),
															  provisionDownloadCouponDTO.getProvisionDownloadCouponID());

			if(result <= 0) {
				log.error("updateAdminCouponDownloadImageID 실패");
				return false;

			}

			log.trace("updateAdminCouponDownloadImageID 성공");
			return true;

		}
		else if (provisionDownloadCouponDTO.getSearchCondition().equals("updateAdminCouponDownloadData")) {

			log.trace("updateAdminCouponDownloadData 진입");

			int result = jdbcTemplate.update(UPDATE, provisionDownloadCouponDTO.getDeployDeadline(),
													 provisionDownloadCouponDTO.getProvisionDownloadCouponID());

			if(result <= 0) {
				log.error("updateAdminCouponDownloadData 실패");
				return false;

			}

			log.trace("updateAdminCouponDownloadData 성공");
			return true;

		}
		else if (provisionDownloadCouponDTO.getSearchCondition().equals("stopAdminCouponDownloadData")) {

			log.trace("stopAdminCouponDownloadData 진입");

			int result = jdbcTemplate.update(UPDATE_DEPLOY_STATUS, provisionDownloadCouponDTO.getProvisionDownloadCouponID());

			if(result <= 0) {
				log.error("stopAdminCouponDownloadData 실패");
				return false;

			}

			log.trace("stopAdminCouponDownloadData 성공");
			return true;
		}
		
		log.error("update 실패");
		return false;
	}
	
	
	public boolean delete(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
			return false;

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
		data.setCouponID(rs.getInt("C.COUPON_ID"));
		data.setAncImagePath(rs.getString("I.IMAGE_PATH"));

		log.trace("ProvisionDownloadCouponRowMapper 처리 완료");

		return data;

	}

}


@Slf4j
class selectOneImageIdRowMapper implements RowMapper<ProvisionDownloadCouponDTO> {

	@Override
	public ProvisionDownloadCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.trace("selectOneImageIdRowMapper 처리 진입");

		ProvisionDownloadCouponDTO data = new ProvisionDownloadCouponDTO();

		data.setAncImageID(rs.getInt("IMAGE_ID"));

		log.trace("ProvisionDownloadCouponRowMapper 처리 완료");

		return data;

	}

}
