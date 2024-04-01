package com.naeddoco.nsmwspring.model.provisionBatchCouponModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("provisionBatchCouponDAO")
@Slf4j
public class ProvisionBatchCouponDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	private static final String SELECTALL = "";
	
	private static final String SELECTONE = "";
	
	private static final String INSERT = "INSERT INTO PROVISION_BATCH_COUPON (COUPON_ID, DEPLOY_STATUS) "
										+ "VALUES (?,?)";
	
	
	//전체발송쿠폰 정보 변경
	private static final String UPDATE = "UPDATE PROVISION_BATCH_COUPON "
										+ "SET "
											+ "	DEPLOY_STATUS = ? "
										+ "WHERE PROVISION_BATCH_COUPON_ID = ? ";
	

	//전체 발송쿠폰 배포상태 = 중단으로 변경
	private static final String UPDATE_DEPLOY_STATUS = "UPDATE PROVISION_BATCH_COUPON "
													 + "SET DEPLOY_STATUS = 'STOP' "
													 + "WHERE PROVISION_BATCH_COUPON_ID = ?";
	

//------------------------------------------------------------------------------------------------
	public List<ProvisionBatchCouponDTO> selectAll(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
//		log.trace("selectAll 진입");
//
//			log.trace("selectAllCouponInfo 진입");
//
//			try {
//
//				return (List<ProvisionBatchCouponDTO>)jdbcTemplate.query(SELECTALL, new ProvisionBatchCouponRowMapper());
//
//			} catch (Exception e) {
//
//				log.error("selectAllCoupon 예외/실패");

				return null;

//			}

	}

	
	public ProvisionBatchCouponDTO selectOne(ProvisionBatchCouponDTO provisionBatchCouponDTO) {

//		Object[] args = { provisionBatchCouponDTO.getProvisionBatchCouponID()};
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new ProvisionBatchCouponRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(ProvisionBatchCouponDTO provisionBatchCouponDTO) {

		log.trace("insert 진입");

		if (provisionBatchCouponDTO.getSearchCondition().equals("insertAdminCouponBatchDatas")) {
			
			log.trace("insertAdminCouponBatchDatas 처리 진입");
			
			int result = jdbcTemplate.update(INSERT,provisionBatchCouponDTO.getCouponID(),
													provisionBatchCouponDTO.getDeployStatus());
			
			if(result <= 0) {
				
				log.error("insertAdminCouponBatchDatas 실패");
				return false;
			
			}
			
			log.trace("insertAdminCouponBatchDatas 성공");
			return true;
		}
		
		log.error("insert 실패");
		return false;
	}
	
	
	public boolean update(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
	
		log.trace("update 진입");
		
		if (provisionBatchCouponDTO.getSearchCondition().equals("updateAdminCouponBatchDatas")) {
			
			int result = jdbcTemplate.update(UPDATE, provisionBatchCouponDTO.getDeployStatus(),
												     provisionBatchCouponDTO.getProvisionBatchCouponID());

			if(result <= 0) {
				log.error("updateAdminCouponBatchDatas 실패");
				return false;

			}

			log.trace("updateAdminCouponBatchDatas 성공");
			return true;
		} 
		else if (provisionBatchCouponDTO.getSearchCondition().equals("stopAdminCouponBatchDatas")) {
			
			int result = jdbcTemplate.update(UPDATE_DEPLOY_STATUS, provisionBatchCouponDTO.getProvisionBatchCouponID());

			if(result <= 0) {
				
				log.error("stopAdminCouponBatchDatas 실패");
				return false;

			}

			log.trace("stopAdminCouponBatchDatas 성공");
			return true;
			
		}
		
		log.error("update 실패");
		return false;
	}
	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class ProvisionBatchCouponRowMapper implements RowMapper<ProvisionBatchCouponDTO> {
	@Override
	public ProvisionBatchCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProvisionBatchCouponDTO data = new ProvisionBatchCouponDTO();
		
		data.setProvisionBatchCouponID(rs.getInt("PROVISION_BATCH_COUPON_ID"));
		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setDeployStatus(rs.getString("DEPLOY_STATUS"));
			
		log.debug(Integer.toString(rs.getInt("PROVISION_BATCH_COUPON_ID")));
		log.debug(Integer.toString(rs.getInt("COUPON_ID")));
		log.debug(rs.getString("DEPLOY_STATUS"));
		
		return data;
	}
	
}
