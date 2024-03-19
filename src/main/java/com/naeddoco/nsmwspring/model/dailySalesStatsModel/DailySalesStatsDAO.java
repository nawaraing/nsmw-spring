package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("dailySalesStatsDAO")
@Slf4j
public class DailySalesStatsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";

	private static final String INSERT = "";

	private static final String UPDATE = "";

	private static final String DELETE = "";


	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO) {
		log.debug("selectAll start");
		return (List<DailySalesStatsDTO>)jdbcTemplate.query(SELECTALL, new DailySalesStatsRowMapper());
	}


	public DailySalesStatsDTO selectOne(DailySalesStatsDTO dailySalesStatsDTO) {

		//	Object[] args = { dailySalesStatsDTO.getDailySalesStatsID() };
		//			log.debug("selectOne start");
		//	
		//			try {
		//				return jdbcTemplate.queryForObject(SELECTONE, args, new DailySalesStatsRowMapper());
		//			} catch (Exception e) {
		//				log.debug("selectOne 예외처리");
				return null;
		//			}
	}


	public boolean insert(DailySalesStatsDTO dailySalesStatsDTO) {

		//			int result = jdbcTemplate.update(INSERT);
		//			if(result <= 0) {
		//				log.debug("insert 실패");
				return false;
		//			}
		//			log.debug("insert 성공");
		//			return true;
	}


	public boolean update(DailySalesStatsDTO dailySalesStatsDTO) {

		//			int result = jdbcTemplate.update(UPDATE);
		//			if(result <= 0) {
		//				log.debug("update 실패");
				return false;
		//			}
		//			log.debug("update 성공");
		//			return true;
	}


	public boolean delete(DailySalesStatsDTO dailySalesStatsDTO) {

		//			int result = jdbcTemplate.update(DELETE);
		//			if(result <= 0) {
		//				log.debug("delete 성공");
				return false;
		//			}
		//			log.debug("delete 성공");
		//			return true;
	}	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class DailySalesStatsRowMapper implements RowMapper<DailySalesStatsDTO> {
	
	@Override
	public DailySalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DailySalesStatsDTO data = new DailySalesStatsDTO();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		data.setDailySalesStatsID(rs.getInt("DAILY_SALES_STATS_ID"));
		data.setDailyTotalCalculateDate(rs.getTimestamp("DAILY_TOTAL_CALCULATE_DATE"));
		data.setDailyTotalGrossMargine(rs.getInt("DAILY_TOTAL_GROSS_MARGINE"));
		data.setDailyTotalNetProfit(rs.getInt("DAILY_TOTAL_NET_PROFIT"));

		log.debug(Integer.toString(rs.getInt("DAILY_SALES_STATS_ID")));
		log.debug(sdf.format(rs.getTimestamp("DAILY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_NET_PROFIT")));

		return data;
	}
}
