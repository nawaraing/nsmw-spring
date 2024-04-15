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


	//관리자 페이지-상품별 판매 통계 조회(일자별)
	//날짜와 시작행의 인덱스, 보여줄 상품 갯수를 입력받아 조회
	//최대 검색일자 범위는 컨트롤러에서 확인 예정
	private static final String SELECTALL_ADMIN_STAT_DATA = "SELECT "
																+ "DPS.PRODUCT_ID, "
																+ "P.PRODUCT_NAME ,"
																+ "SUM(DPS.DAILY_TOTAL_QUANTITY) AS DAILY_TOTAL_QUANTITY, "
																+ "SUM(DPS.DAILY_TOTAL_GROSS_MARGINE) AS DAILY_TOTAL_GROSS_MARGINE, "
																+ "SUM(DPS.DAILY_TOTAL_NET_PROFIT) AS DAILY_TOTAL_NET_PROFIT "
															+ "FROM "
																+ "DAILY_PRODUCT_SALES_STATS DPS "
															+ "JOIN "
																+ "PRODUCT P ON DPS.PRODUCT_ID = P.PRODUCT_ID "
															+ "WHERE "
																+ "DPS.DAILY_TOTAL_CALCULATE_DATE BETWEEN ? AND ? "
																+ "GROUP BY DPS.PRODUCT_ID, P.PRODUCT_NAME";

		

	
	//각 제품의 일별 판매 통계 - 일정 시간에 자동 추가
	//이벤트 스케쥴러 사용함
	/* private static final String INSERT = "INSERT INTO DAILY_PRODUCT_SALES_STATS ("
											+ "PRODUCT_ID,"
											+ "DAILY_TOTAL_CALCULATE_DATE, "
											+ "DAILY_TOTAL_QUANTITY, "
											+ "DAILY_TOTAL_GROSS_MARGINE,"
											+ "DAILY_TOTAL_NET_PROFIT) "
										+ "SELECT "
											+ "O.PRODUCT_ID,"
											+ "DATE(B.BUY_DATE) AS DAILY_TOTAL_CALCULATE_DATE, "
											+ "SUM(O.BUY_QUANTITY) AS DAILY_TOTAL_QUANTITY, "
											+ "SUM(O.PAYMENT_PRICE) AS DAILY_TOTAL_GROSS_MARGINE, "
											+ "SUM(O.PAYMENT_PRICE) - (SUM(O.BUY_QUANTITY) * P.COST_PRICE) AS DAILY_TOTAL_NET_PROFIT "
										+ "FROM "
											+ "ORDER_INFO O "
										+ "JOIN BUY_INFO B ON O.BUY_INFO_ID = B.BUY_INFO_ID "
										+ "JOIN PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID "
										+ "GROUP BY "
											+ "O.PRODUCT_ID, DATE(B.BUY_DATE)"; */
	
	
/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	
	
	public List<DailyProductSalesStatsDTO> selectAll(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {

		log.trace("selectAll 진입");
		
		if(dailyProductSalesStatsDTO.getSearchCondition().equals("selectAdminStatProductDatas")) {

			Object[] args = { dailyProductSalesStatsDTO.getAncStartDate(), 
							  dailyProductSalesStatsDTO.getAncEndDate() };

			log.trace("selectAdminStatProductDatas 진입");
			
			try {
				return (List<DailyProductSalesStatsDTO>)jdbcTemplate.query(SELECTALL_ADMIN_STAT_DATA, args, new SelectAdminDailyProductSalesStatsRowMapper());
			}
			catch (Exception e) {

				log.error("selectAdminStatProductDatas 예외/실패 ");
				return null;
			}
		}
		
		log.error("selectAll 실패");
		return null;
	}
	
}


/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/


@Slf4j
class SelectAdminDailyProductSalesStatsRowMapper implements RowMapper<DailyProductSalesStatsDTO> {
	@Override
	public DailyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DailyProductSalesStatsDTO data = new DailyProductSalesStatsDTO();
		
		log.trace("SelectAdminDailyProductSalesStatsRowMapper 진입");
		
		data.setProductID(rs.getInt("DPS.PRODUCT_ID"));
		data.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		data.setDailyTotalQuantity(rs.getInt("DAILY_TOTAL_QUANTITY"));
		data.setDailyTotalGrossMargine(rs.getInt("DAILY_TOTAL_GROSS_MARGINE"));
		data.setDailyTotalNetProfit(rs.getInt("DAILY_TOTAL_NET_PROFIT"));
			
		log.debug("PRODUCT_ID : " + Integer.toString(rs.getInt("DPS.PRODUCT_ID")));
		log.debug("PRODUCT_NAME : " + rs.getString("P.PRODUCT_NAME"));
		log.debug("DAILY_TOTAL_QUANTITY : " + Integer.toString(rs.getInt("DAILY_TOTAL_QUANTITY")));
		log.debug("DAILY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("DAILY_TOTAL_GROSS_MARGINE")));
		log.debug("DAILY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("DAILY_TOTAL_NET_PROFIT")));
		
		log.trace("SelectAdminDailyProductSalesStatsRowMapper 완료");
		
		return data;
	}
}
