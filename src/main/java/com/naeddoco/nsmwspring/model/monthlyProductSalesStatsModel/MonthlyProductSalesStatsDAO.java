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
	
	//관리자 페이지-상품별 판매 통계 조회(월별)
	//날짜(yyyy-mm-01)를 입력받아 조회
	//최대 검색일자 범위는 컨트롤러에서 확인 예정
	private static final String SELECTALL_ADMIN_STAT_DATA = "SELECT "
																+ "MPS.PRODUCT_ID, "
																+ "P.PRODUCT_NAME, "
																+ "SUM(MPS.MONTHLY_TOTAL_QUANTITY) AS MONTHLY_TOTAL_QUANTITY , "
																+ "SUM(MPS.MONTHLY_TOTAL_GROSS_MARGINE) AS MONTHLY_TOTAL_GROSS_MARGINE, "
																+ "SUM(MPS.MONTHLY_TOTAL_NET_PROFIT) AS MONTHLY_TOTAL_NET_PROFIT "
																+ "FROM "
																	+ "MONTHLY_PRODUCT_SALES_STATS MPS "
																+ "JOIN "
																	+ "PRODUCT P ON MPS.PRODUCT_ID = P.PRODUCT_ID "
																+ "WHERE "
																	+ "MPS.MONTHLY_TOTAL_CALCULATE_DATE BETWEEN DATE_FORMAT(?, '%Y-%m-01') AND LAST_DAY(?) "
																+ "GROUP BY MPS.PRODUCT_ID, P.PRODUCT_NAME";
	
	
	//각 제품의 월별 판매 통계 - 일정 시간에 자동 추가
	//매월 초, 전월의 판매 통계를 전월의 1일자로 저장함 
	//이벤트 스케쥴러 사용 예정
	/* private static final String INSERT = "INSERT INTO MONTHLY_PRODUCT_SALES_STATS "
											+ "(PRODUCT_ID, "
											+ "MONTHLY_TOTAL_CALCULATE_DATE, "
											+ "MONTHLY_TOTAL_QUANTITY, "
											+ "MONTHLY_TOTAL_GROSS_MARGINE, "
											+ "MONTHLY_TOTAL_NET_PROFIT)"
										+ "SELECT "
											+ "PRODUCT_ID, "
											+ "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01') AS MONTHLY_TOTAL_CALCULATE_DATE, "
											+ "SUM(DAILY_TOTAL_QUANTITY) AS MONTHLY_TOTAL_QUANTITY, "
											+ "SUM(DAILY_TOTAL_GROSS_MARGINE) AS MONTHLY_TOTAL_GROSS_MARGINE, "
											+ "SUM(DAILY_TOTAL_NET_PROFIT) AS MONTHLY_TOTAL_NET_PROFIT "
										+ "FROM "
											+ "DAILY_PRODUCT_SALES_STATS "
										+ "WHERE "
											+ "DAILY_TOTAL_CALCULATE_DATE BETWEEN DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01') AND LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)) "
										+ "GROUP BY PRODUCT_ID ";
	*/
	
	//샘플데이터 추가시 사용
	private static final String INSERT_SAMPLE_DATA = "INSERT INTO MONTHLY_PRODUCT_SALES_STATS "
											+ "(PRODUCT_ID, MONTHLY_TOTAL_CALCULATE_DATE, MONTHLY_TOTAL_QUANTITY, MONTHLY_TOTAL_GROSS_MARGINE, MONTHLY_TOTAL_NET_PROFIT) "
											+ "SELECT "
												+ "PRODUCT_ID, "
												+ "DATE_FORMAT(?, '%Y-%m-01') AS MONTHLY_TOTAL_CALCULATE_DATE, "
												+ "SUM(DAILY_TOTAL_QUANTITY) AS MONTHLY_TOTAL_QUANTITY, "
												+ "SUM(DAILY_TOTAL_GROSS_MARGINE) AS MONTHLY_TOTAL_GROSS_MARGINE, "
												+ "SUM(DAILY_TOTAL_NET_PROFIT) AS MONTHLY_TOTAL_NET_PROFIT "
											+ "FROM "
												+ "DAILY_PRODUCT_SALES_STATS "
											+ "WHERE "
												+ "DAILY_TOTAL_CALCULATE_DATE BETWEEN DATE_FORMAT(?, '%Y-%m-01') "
												+ "AND LAST_DAY(?) "
											+ "GROUP BY "
												+ "PRODUCT_ID";
	
	

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	

	
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

		log.trace("selectAll 진입");

		if(monthlyProductSalesStatsDTO.getSearchCondition().equals("selectAdminStatProductDatas")) {

			Object[] args = { monthlyProductSalesStatsDTO.getAncStartMonth(), monthlyProductSalesStatsDTO.getAncEndMonth()};

			log.trace("selectAdminStatProductDatas 진입");
			log.debug("AncStartMonth : " + monthlyProductSalesStatsDTO.getAncStartMonth());
			log.debug("AncEndMonth : " + monthlyProductSalesStatsDTO.getAncEndMonth());
			
			try {
				
				return (List<MonthlyProductSalesStatsDTO>)jdbcTemplate.query(SELECTALL_ADMIN_STAT_DATA, args, new SelectAdminMonthlyProductSalesStatsRowMapper());
		
			}
			catch (Exception e) {
				
				log.error("selectAdminStatProductDatas 예외/실패 "+ e.getMessage(), e);
				return null;
			}
		}
		
		log.error("selectAll 실패");
		return null;
	}
	

/*-----------------------------------[ insert ] ------------------------------------------------------------------------------------------------------------*/

	
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

		log.trace("insert 진입");

		int result = 0;

		try {

			result = jdbcTemplate.update(INSERT_SAMPLE_DATA, 
											monthlyProductSalesStatsDTO.getMonthlyTotalCalculateDate(),
											monthlyProductSalesStatsDTO.getMonthlyTotalCalculateDate(),
											monthlyProductSalesStatsDTO.getMonthlyTotalCalculateDate());

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
class MonthlyProductSalesStatsRowMapper implements RowMapper<MonthlyProductSalesStatsDTO> {
	@Override
	public MonthlyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonthlyProductSalesStatsDTO data = new MonthlyProductSalesStatsDTO();
		
		log.trace("MonthlyProductSalesStatsRowMapper 진입");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.setMonthlyProductSalesStatsID(rs.getInt("MONTHLY_PRODUCT_SALES_STATS_ID"));
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setMonthlyTotalCalculateDate(rs.getDate("MONTHLY_TOTAL_CALCULATE_DATE"));
		data.setMonthlyTotalQuantity(rs.getInt("MONTHLY_TOTAL_QUANTITY"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MONTHLY_TOTAL_NET_PROFIT"));
			
		log.debug("MONTHLY_PRODUCT_SALES_STATS_ID : " + Integer.toString(rs.getInt("MONTHLY_PRODUCT_SALES_STATS_ID")));
		log.debug("PRODUCT_ID : " + Integer.toString(rs.getInt("PRODUCT_ID")));
		log.debug("MONTHLY_TOTAL_CALCULATE_DATE : " + sdf.format(rs.getTimestamp("MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug("MONTHLY_TOTAL_QUANTITY : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_QUANTITY")));
		log.debug("MONTHLY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug("MONTHLY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		log.trace("MonthlyProductSalesStatsRowMapper 완료");
		
		return data;
	}
	
}

@Slf4j
class SelectAdminMonthlyProductSalesStatsRowMapper implements RowMapper<MonthlyProductSalesStatsDTO> {
	@Override
	public MonthlyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonthlyProductSalesStatsDTO data = new MonthlyProductSalesStatsDTO();
		
		data.setProductID(rs.getInt("MPS.PRODUCT_ID"));
		data.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		data.setMonthlyTotalQuantity(rs.getInt("MONTHLY_TOTAL_QUANTITY"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MONTHLY_TOTAL_NET_PROFIT"));
			
		log.debug("MPS.PRODUCT_ID : " + Integer.toString(rs.getInt("MPS.PRODUCT_ID")));
		log.debug("PRODUCT_NAME : " + rs.getString("P.PRODUCT_NAME"));
		log.debug("MONTHLY_TOTAL_QUANTITY : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_QUANTITY")));
		log.debug("MONTHLY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug("MONTHLY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("MONTHLY_TOTAL_NET_PROFIT")));
		
		
		return data;
	}
}
