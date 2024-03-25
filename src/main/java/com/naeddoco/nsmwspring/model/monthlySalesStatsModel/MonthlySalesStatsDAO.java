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

	private static final String SELECTALL = "SELECT MONTHLY_SALES_STATS_ID, "
												+ "MONTHLY_TOTAL_CALCULATE_DATE, "
												+ "MONTHLY_TOTAL_GROSS_MARGINE, "
												+ "MONTHLY_TOTAL_NET_PROFIT "
											+ "FROM MONTHLY_SALES_STATS "
											+ "WHERE "
												+ "MONTHLY_TOTAL_CALCULATE_DATE BETWEEN DATE_FORMAT( ?, '%Y-%m-01') AND LAST_DAY(?)"
											+ "ORDER BY "
												+ "MONTHLY_TOTAL_CALCULATE_DATE "
											+ "LIMIT ?, ?";

	private static final String SELECTONE = "";
	
	//매월 초 전월의 판매 통계를 전월의 1일자로 저장함 
	private static final String INSERT = "INSERT INTO MONTHLY_SALES_STATS "
											+ "(MONTHLY_TOTAL_CALCULATE_DATE, MONTHLY_TOTAL_GROSS_MARGINE, MONTHLY_TOTAL_NET_PROFIT) "
											+ "SELECT "
												+ "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01'), "
												+ "SUM(MONTHLY_TOTAL_GROSS_MARGINE), "
												+ "SUM(MONTHLY_TOTAL_NET_PROFIT) "
											+ "FROM "
												+ "MONTHLY_PRODUCT_SALES_STATS "
											+ "WHERE "
											+ "MONTHLY_TOTAL_CALCULATE_DATE >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01')"
											+ "AND MONTHLY_TOTAL_CALCULATE_DATE <= LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		log.trace("selectAll 진입");
		
		//월별 판매 통계 조회
		if(monthlySalesStatsDTO.getSearchCondition().equals("selectAdminStatDateDatas")) {
			log.trace("selectAdminStatDateDatas 진입");
			
			try {
				return (List<MonthlySalesStatsDTO>)jdbcTemplate.query(SELECTALL, new MonthlySalesStatsRowMapper());
			} 
			catch (Exception e){
				log.error("selectAdminStatDateDatas 예외/실패");

				return null;
			}
		}
		log.error("selectAll 실패");
		return null;
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
		
		log.trace("insert 진입");
		if(monthlySalesStatsDTO.getSearchCondition().equals("insertAdminStatDateDatas")) {
			
			log.trace("insertAdminStatDateDatas 진입");
			int result = jdbcTemplate.update(INSERT, monthlySalesStatsDTO.getMonthlyTotalCalculateDate(), 
													monthlySalesStatsDTO.getMonthlyTotalGrossMargine(), 
													monthlySalesStatsDTO.getMonthlyTotalNetProfit());
			if(result <= 0) {
				log.error("insertAdminStatDateDatas 실패");
				return false;
			}
			log.trace("insertAdminStatDateDatas 성공");
			return true;
		}
		
		log.error("insert 실패");
	return false;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.setMonthlySalesStatsID(rs.getInt("MONTHLY_SALES_STATS_ID"));
		data.setMonthlyTotalCalculateDate(rs.getDate("MONTHLY_TOTAL_CALCULATE_DATE"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MONTHLY_TOTAL_NET_PROFIT"));
			
		log.debug(Integer.toString(rs.getInt("MONTHLY_SALES_STATS_ID")));
		log.debug(sdf.format(rs.getDate("MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug(Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		return data;
	}
	
}
