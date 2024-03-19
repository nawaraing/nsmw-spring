package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import lombok.extern.slf4j.Slf4j;

@Repository("monthlySalesStatsDAO")
@Slf4j
public class MonthlySalesStatsDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		log.debug("selectAll start");
		return (List<MonthlySalesStatsDTO>)jdbcTemplate.query(SELECTALL, new MonthlySalesStatsRowMapper());
	}

	
	public MonthlySalesStatsDTO selectOne(MonthlySalesStatsDTO monthlySalesStatsDTO) {

//		Object[] args = { monthlySalesStatsDTO.getMonthlySalesStatsID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new MonthlySalesStatsRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(MonthlySalesStatsDTO monthlySalesStatsDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(MonthlySalesStatsDTO monthlySalesStatsDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		
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
class MonthlySalesStatsRowMapper implements RowMapper<MonthlySalesStatsDTO> {
	@Override
	public MonthlySalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MonthlySalesStatsDTO data = new MonthlySalesStatsDTO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		data.setMonthlySalesStatsID(rs.getInt("MONTHLY_SALES_STATS_ID"));
		data.setMonthlyTotalCalculateDate(rs.getTimestamp("MONTHLY_TOTAL_CALCULATE_DATE"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MONTHLY_TOTAL_NET_PROFIT"));
			
		log.debug(Integer.toString(rs.getInt("MONTHLY_SALES_STATS_ID")));
		log.debug(sdf.format(rs.getTimestamp("MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		return data;
	}
	
}
