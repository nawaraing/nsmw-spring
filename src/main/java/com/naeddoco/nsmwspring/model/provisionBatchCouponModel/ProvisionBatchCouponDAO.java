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

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
//------------------------------------------------------------------------------------------------
	public List<ProvisionBatchCouponDTO> selectAll(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		log.debug("selectAll start");
		return (List<ProvisionBatchCouponDTO>)jdbcTemplate.query(SELECTALL, new ProvisionBatchCouponRowMapper());
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
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(ProvisionBatchCouponDTO provisionBatchCouponDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		
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
