package com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("monthlyProductSalesStatsDAO")
@Slf4j
public class MonthlyProductSalesStatsDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		log.debug("selectAll start");
		return (List<MonthlyProductSalesStatsDTO>)jdbcTemplate.query(SELECTALL, new MonthlyProductSalesStatsRowMapper());
	}

	
	public MonthlyProductSalesStatsDTO selectOne(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

//		Object[] args = { monthlyProductSalesStatsDTO.getMonthlyProductSalesStatsID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new MonthlyProductSalesStatsRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		
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
class MonthlyProductSalesStatsRowMapper implements RowMapper<MonthlyProductSalesStatsDTO> {
	@Override
	public MonthlyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonthlyProductSalesStatsDTO data = new MonthlyProductSalesStatsDTO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		data.setMonthlyProductSalesStatsID(rs.getInt("MONTHLY_PRODUCT_SALES_STATS_ID"));
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setMonthlyTotalCalculateDate(rs.getTimestamp("MONTHLY_TOTAL_CALCULATE_DATE"));
		data.setMonthlyTotalQuantity(rs.getInt("MONTHLY_TOTAL_QUANTITY"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MONTHLY_TOTAL_NET_PROFIT"));
			
		
		log.debug(Integer.toString(rs.getInt("MONTHLY_PRODUCT_SALES_STATS_ID")));
		log.debug(Integer.toString(rs.getInt("PRODUCT_ID")));
		log.debug(sdf.format(rs.getTimestamp("MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_QUANTITY")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		return data;
	}
	
}

