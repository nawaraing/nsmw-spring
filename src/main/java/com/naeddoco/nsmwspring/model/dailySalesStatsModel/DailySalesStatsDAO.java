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

	//관리자 메인 대시보드 화면에서
	//최근 일주일의 일자별 요약 출력
	private static final String SELECTALL_DASHBOARD_DATA = "SELECT DAILY_SALES_STATS_ID, "
															+ "DAILY_TOTAL_CALCULATE_DATE, "
															+ "DAILY_TOTAL_GROSS_MARGINE, "
															+ "DAILY_TOTAL_NET_PROFIT "
														+ "FROM DAILY_SALES_STATS "
														+ "ORDER BY DAILY_TOTAL_CALCULATE_DATE DESC "
														+ "LIMIT 7";
	
	//기간별 판매 통계 조회
	private static final String SELECTALL_ADMIN_STAT_DATA = "SELECT "
															+ "DAILY_SALES_STATS_ID, "
															+ "DAILY_TOTAL_CALCULATE_DATE, "
															+ "DAILY_TOTAL_GROSS_MARGINE, "
															+ "DAILY_TOTAL_NET_PROFIT "
														+ "FROM "
															+ "DAILY_SALES_STATS "
														+ "WHERE "
															+ "DAILY_TOTAL_CALCULATE_DATE BETWEEN ? AND ? "
														+ "ORDER BY "
															+ "DAILY_TOTAL_CALCULATE_DATE ";

	private static final String SELECTONE = "";

	//전일의 매출 통계를 추가하는 쿼리
	//DAILY_PRODUCT_SALES_STATS 테이블에 만들어놓은 매출과 이익의 합을
	//해당 일자에 추가함
	//시스템에서 자동으로 추가되게 구현할 예정
	private static final String INSERT = "INSERT INTO DAILY_SALES_STATS "
											+ "(DAILY_TOTAL_CALCULATE_DATE,"
											+ "DAILY_TOTAL_GROSS_MARGINE, "
											+ "DAILY_TOTAL_NET_PROFIT) "
										+ "SELECT DATE_SUB(CURDATE(), INTERVAL 1 DAY), "
											+ "SUM(DAILY_TOTAL_GROSS_MARGINE), "
											+ "SUM(DAILY_TOTAL_NET_PROFIT) "
										+ "FROM DAILY_PRODUCT_SALES_STATS "
										+ "WHERE DAILY_TOTAL_CALCULATE_DATE = DATE_SUB(CURDATE(), INTERVAL 1 DAY) "
										+ "GROUP BY DAILY_TOTAL_CALCULATE_DATE";

	
	private static final String UPDATE = "";

	private static final String DELETE = "";


	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO) {
		log.trace("selectAll 진입");
		
		if(dailySalesStatsDTO.getSearchCondition().equals("selectDashboardDatas")) {
			
			log.trace("selectDashboardDatas 진입");
			
			try {
				return (List<DailySalesStatsDTO>)jdbcTemplate.query(SELECTALL_DASHBOARD_DATA, new DailySalesStatsRowMapper());
				
			} catch (Exception e) {

				log.error("selectDashboardDatas 예외/실패");

				return null;
			}
		}
		else if(dailySalesStatsDTO.getSearchCondition().equals("selectAdminStatDateDatas")) {
			
			Object[] args = { dailySalesStatsDTO.getAncStartDate(), dailySalesStatsDTO.getAncEndDate()};

			log.trace("selectAdminStatDateDatas 진입");
			
			try {
				return (List<DailySalesStatsDTO>)jdbcTemplate.query(SELECTALL_ADMIN_STAT_DATA, args, new DailySalesStatsRowMapper());
				
			} catch (Exception e) {

				log.error("selectAdminStatDateDatas 예외/실패");

				return null;
			}
		}
		
		log.error("selectAll 실패");
		return null;
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

		log.debug("insert 진입");
		
		int result = jdbcTemplate.update(INSERT);
		
		if(result <= 0) {
			log.error("insert 실패");
			return false;
		}
		log.debug("insert 성공");
		return true;
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
		
		log.debug("DailySalesStatsRowMapper 진입");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		data.setDailySalesStatsID(rs.getInt("DAILY_SALES_STATS_ID"));
		data.setDailyTotalCalculateDate(rs.getDate("DAILY_TOTAL_CALCULATE_DATE"));
		data.setDailyTotalGrossMargine(rs.getInt("DAILY_TOTAL_GROSS_MARGINE"));
		data.setDailyTotalNetProfit(rs.getInt("DAILY_TOTAL_NET_PROFIT"));

		log.debug(Integer.toString(rs.getInt("DAILY_SALES_STATS_ID")));
		log.debug(sdf.format(rs.getDate("DAILY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("DAILY_TOTAL_NET_PROFIT")));

		return data;
	}
}

