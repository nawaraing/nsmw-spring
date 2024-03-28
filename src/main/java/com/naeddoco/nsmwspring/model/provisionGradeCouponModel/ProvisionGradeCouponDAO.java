package com.naeddoco.nsmwspring.model.provisionGradeCouponModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("provisionGradeCouponDAO")
@Slf4j
public class ProvisionGradeCouponDAO {
	
		@Autowired
		private JdbcTemplate jdbcTemplate;

		private static final String SELECTALL = "SELECT PROVISION_GRADE_COUPON_ID, GRADE_ID, COUPON_ID, DEPLOY_CYCLE, DEPLOY_BASE "
												+ "FROM PROVISION_GRADE_COUPON";
		
		private static final String SELECTONE = "";

		// COUPON insert시 PROVISION_GRADE_COUPON에도 함께 insert
		private static final String INSERT = "INSERT INTO PROVISION_GRADE_COUPON "
											+ "(GRADE_ID, COUPON_ID, DEPLOY_CYCLE, DEPLOY_BASE) "
											+ "VALUES (?,?,?,?)";

		private static final String UPDATE = "UPDATE PROVISION_GRADE_COUPON "
											+ "SET GRADE_ID = ? ,"
												+ "DEPLOY_CYCLE = ? "
												+ "DEPLOY_BASE = ? "
											+ "WHERE COUPON_ID = ?";
		
		private static final String DELETE = "";

		/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

		public List<ProvisionGradeCouponDTO> selectAll(ProvisionGradeCouponDTO provisionGradeCouponDTO) {

			log.trace("selectAll 진입");

			if (provisionGradeCouponDTO.getSearchCondition().equals("selectAllAdminCouponGradeDatas")) {

				log.trace("selectAllAdminCouponGradeDatas 진입 ");

				try {

					return (List<ProvisionGradeCouponDTO>) jdbcTemplate.query(SELECTALL, new ProvisionGradeCouponRowMapper());

				} catch (Exception e) {

					log.error("selectAllAdminCouponGradeDatas 예외/실패 ");

					return null;

				}

			}

			log.error("selectAll 실패");

			return null;

		}

		/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

		public ProvisionGradeCouponDTO selectOne(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
			
//			log.debug("[로그] 처리 진입");
//			
//			if(provisionGradeCouponDTO.getSearchCondition().equals("getProductDetail")) {
//				
//				log.debug("[로그]처리 진입");
//				
//				Object[] args = { provisionGradeCouponDTO.getProvisionGradeCouponID() };
//
//				try {
//
//					return jdbcTemplate.queryForObject(SELECTONE, args, new ProvisionGradeCouponRowMapper());
//
//				} catch (Exception e) {
//					
//					log.debug("[로그] 예외 발생");
//
//					return null;
//
//				}
//				
//			}
//			
//			log.debug("[로그] 처리 실패");
			
			return null;

		}

		/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

		public boolean insert(ProvisionGradeCouponDTO provisionGradeCouponDTO) {

			log.trace("insert 처리 진입");

			int result = 0;

			if (provisionGradeCouponDTO.getSearchCondition().equals("insertAdminCouponGradeData")) {
				log.trace("insertAdminCouponGradeData 처리 진입");

				result = jdbcTemplate.update(INSERT,provisionGradeCouponDTO.getGradeID(),
													provisionGradeCouponDTO.getCouponID(),
													provisionGradeCouponDTO.getDeployCycle(),
													provisionGradeCouponDTO.getDeployBase());

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

		public boolean update(ProvisionGradeCouponDTO provisionGradeCouponDTO) {

			log.trace("update 진입");

			if (provisionGradeCouponDTO.getSearchCondition().equals("updateAdminCouponGradeData")) {

				log.trace("updateAdminCouponGradeData 진입");

				int result = jdbcTemplate.update(UPDATE, provisionGradeCouponDTO.getGradeID(), 
														provisionGradeCouponDTO.getDeployCycle(),
														provisionGradeCouponDTO.getDeployBase(),
														provisionGradeCouponDTO.getCouponID());

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
		
		
		public boolean delete(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
//			int result = jdbcTemplate.update(UPDATE);
//
//			if (result <= 0) {

				return false;

//			}
//
//			return true;

		}


	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	@Slf4j
	class ProvisionGradeCouponRowMapper implements RowMapper<ProvisionGradeCouponDTO> {

		@Override
		public ProvisionGradeCouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

			log.trace("ProvisionGradeCouponRowMapper 처리 진입");

			ProvisionGradeCouponDTO provisionGradeCouponDTO = new ProvisionGradeCouponDTO();

			provisionGradeCouponDTO.setProvisionGradeCouponID(rs.getInt("PROVISION_GRADE_COUPON_ID"));
			provisionGradeCouponDTO.setGradeID(rs.getInt("GRADE_ID"));
			provisionGradeCouponDTO.setCouponID(rs.getInt("COUPON_ID"));
			provisionGradeCouponDTO.setDeployCycle(rs.getString("DEPLOY_CYCLE"));
			provisionGradeCouponDTO.setDeployBase(rs.getString("DEPLOY_BASE"));

			log.trace("ProvisionGradeCouponRowMapper 처리 완료");

			return provisionGradeCouponDTO;

		}

	}
