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

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";

	private static final String INSERT = "";

	private static final String UPDATE = "";

	private static final String DELETE = "";
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<CouponDTO> selectAll(CouponDTO couponDTO) {
		
		log.debug("selectAll start");
		
		return (List<CouponDTO>) jdbcTemplate.query(SELECTALL, new CouponRowMapper());
		
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public CouponDTO selectOne(CouponDTO couponDTO) {

		return null;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(CouponDTO couponDTO) {

		return false;

	}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean update(CouponDTO couponDTO) {

		return false;

	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(CouponDTO couponDTO) {

		return false;

	}
	
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class CouponRowMapper implements RowMapper<CouponDTO> {
	@Override
	public CouponDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CouponDTO data = new CouponDTO();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		data.setCouponID(rs.getInt("COUPON_ID"));
		data.setCouponName(rs.getString("COUPON_NAME"));
		data.setCreateDate(rs.getTimestamp("CREATE_DATE"));
		data.setDistributeDate(rs.getTimestamp("DISTRIBUTE_DATE"));
		data.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));
		data.setCouponType(rs.getString("COUPON_TYPE"));

		log.debug(Integer.toString(rs.getInt("COUPON_ID")));
		log.debug(rs.getString("COUPON_NAME"));
		log.debug(sdf.format(rs.getTimestamp("CREATE_DATE")));
		log.debug(sdf.format(rs.getTimestamp("DISTRIBUTE_DATE")));
		log.debug(sdf.format(rs.getTimestamp("EXPIRATION_DATE")));
		log.debug(rs.getString("COUPON_TYPE"));

		return data;
	}

}