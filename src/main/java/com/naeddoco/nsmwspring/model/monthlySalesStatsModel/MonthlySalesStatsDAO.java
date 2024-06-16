package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsDTO;

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
												+ "MONTHLY_TOTAL_CALCULATE_DATE";

	
	// 매월 초 전월의 판매 통계를 전월의 1일자로 저장함 
	// 이벤트 스케줄러 사용 예정
	/* private static final String INSERT = "INSERT INTO MONTHLY_SALES_STATS "
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
	*/
	
	//샘플데이터 추가시 사용
	private static final String INSERT_SAMPLE_DATA = "INSERT INTO MONTHLY_SALES_STATS "
														+ "(MONTHLY_TOTAL_CALCULATE_DATE, MONTHLY_TOTAL_GROSS_MARGINE, MONTHLY_TOTAL_NET_PROFIT) "
													+ "SELECT "
														+ "DATE_FORMAT(?, '%Y-%m-01'), "
														+ "SUM(MONTHLY_TOTAL_GROSS_MARGINE), "
														+ "SUM(MONTHLY_TOTAL_NET_PROFIT) "
													+ "FROM "
														+ "MONTHLY_PRODUCT_SALES_STATS "
													+ "WHERE "
														+ "MONTHLY_TOTAL_CALCULATE_DATE >= DATE_FORMAT(?, '%Y-%m-01') "
														+ "AND MONTHLY_TOTAL_CALCULATE_DATE <= LAST_DAY(?)";
	

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	

	
	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		log.trace("selectAll 진입");
		
		//월별 판매 통계 조회
		if(monthlySalesStatsDTO.getSearchCondition().equals("selectAdminStatDateDatas")) {
			log.trace("selectAdminStatDateDatas 진입");
			
			Object[] args = { monthlySalesStatsDTO.getAncStartMonth(), monthlySalesStatsDTO.getAncEndMonth()};
			
			try {
				return (List<MonthlySalesStatsDTO>)jdbcTemplate.query(SELECTALL, args, new MonthlySalesStatsRowMapper());
			} 
			catch (Exception e){
				
				log.error("selectAdminStatDateDatas 예외/실패");

				return null;
			}
		}
		
		log.error("selectAll 실패");
		return null;
	}
	

	
/*-----------------------------------[ insert ] ------------------------------------------------------------------------------------------------------------*/
	
	
	public boolean insert(MonthlySalesStatsDTO monthlySalesStatsDTO) {

		log.trace("insert 진입");

		int result = 0;

		try {

			result = jdbcTemplate.update(INSERT_SAMPLE_DATA, 
											monthlySalesStatsDTO.getMonthlyTotalCalculateDate(),
											monthlySalesStatsDTO.getMonthlyTotalCalculateDate(),
											monthlySalesStatsDTO.getMonthlyTotalCalculateDate());

		} catch (Exception e) {

			log.error("insert 예외 발생");
			return false;

		}

		if (result <= 0) {

			log.error("insert 실패");
			return false;

		}

		log.trace("insert 성공");
		return true;

	}

}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/


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
			
		log.debug("MONTHLY_SALES_STATS_ID : " + Integer.toString(rs.getInt("MONTHLY_SALES_STATS_ID")));
		log.debug("MONTHLY_TOTAL_CALCULATE_DATE : " + sdf.format(rs.getDate("MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug("MONTHLY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug("MONTHLY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		return data;
	}
	
}
