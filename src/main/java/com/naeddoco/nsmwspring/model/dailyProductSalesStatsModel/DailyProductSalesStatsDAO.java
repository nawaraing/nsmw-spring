package com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("dailyProductSalesStatsDAO")
@Slf4j
public class DailyProductSalesStatsDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";

	private static final String DELETE = "";
	
	public List<DailyProductSalesStatsDTO> selectAll(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		log.debug("selectAll start");
		return (List<DailyProductSalesStatsDTO>)jdbcTemplate.query(SELECTALL, new DailyProductSalesStatsRowMapper());
	}

	
	public DailyProductSalesStatsDTO selectOne(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {

//		Object[] args = { dailyProductSalesStatsDTO.getDailySalesStatsID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new DailyProductSalesStatsRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		
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
class DailyProductSalesStatsRowMapper implements RowMapper<DailyProductSalesStatsDTO> {
	@Override
	public DailyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DailyProductSalesStatsDTO data = new DailyProductSalesStatsDTO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		data.setDailyProductSalesStatsID(rs.getInt("DAILY_PRODUCT_SALES_STATS_ID"));
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setDailyTotalCalculateDate(rs.getTimestamp("DAILY_TOTAL_CALCULATE_DATE"));
		data.setDailyTotalQuantity(rs.getInt("DAILY_TOTAL_QUANTITY"));
		data.setDailyTotalGrossMargine(rs.getInt("DAILY_TOTAL_GROSS_MARGINE"));
		data.setDailyTotalNetProfit(rs.getInt("DAILY_TOTAL_NET_PROFIT"));
			
		log.debug(Integer.toString(rs.getInt("DAILY_SALES_STATS_ID")));
		log.debug(Integer.toString(rs.getInt("PRODUCT_ID")));
		log.debug(sdf.format(rs.getTimestamp("DAILY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_QUANTITY")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_NET_PROFIT")));
		
		return data;
	}
	
}
