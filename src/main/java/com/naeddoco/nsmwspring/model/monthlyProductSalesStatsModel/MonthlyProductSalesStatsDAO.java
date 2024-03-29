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
	//날짜(yyyy-mm-01)와 시작행의 인덱스, 보여줄 상품 갯수를 입력받아 조회
	//최대 검색일자 범위는 컨트롤러에서 확인 예정
	private static final String SELECTALL_ADMIN_STAT_DATA = "SELECT"
											            		+ "MPS.PRODUCT_ID, "
											            		+ "P.PRODUCT_NAME, "
											            		+ "MPS.MONTHLY_TOTAL_CALCULATE_DATE, "
											            		+ "MPS.MONTHLY_TOTAL_QUANTITY, "
											            		+ "MPS.MONTHLY_TOTAL_GROSS_MARGINE, "
											            		+ "MPS.MONTHLY_TOTAL_NET_PROFIT "
											            	+ "FROM "
											            		+ "MONTHLY_PRODUCT_SALES_STATS MPS "
											            	+ "JOIN "
											            		+ "PRODUCT P ON MPS.PRODUCT_ID = P.PRODUCT_ID "
											            	+ "WHERE "
											            		+ "MPS.MONTHLY_TOTAL_CALCULATE_DATE BETWEEN DATE_FORMAT( ?, '%Y-%m-01') AND LAST_DAY(?)";
                                                        
	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	
	//각 제품의 월별 판매 통계 - 일정 시간에 자동 추가
	//매월 초 전월의 판매 통계를 전월의 1일자로 저장함 
	//추후 이벤트 스케쥴러를 사용할 예정
	private static final String INSERT = "INSERT INTO MONTHLY_PRODUCT_SALES_STATS "
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
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

		log.trace("selectAll 진입");

		if(monthlyProductSalesStatsDTO.getSearchCondition().equals("selectAdminStatProductDatas")) {

			Object[] args = { monthlyProductSalesStatsDTO.getAncStartMonth(), monthlyProductSalesStatsDTO.getAncEndMonth(), monthlyProductSalesStatsDTO.getAncStartRow(), monthlyProductSalesStatsDTO.getAncSelectMax() };

			log.trace("selectAdminStatProductDatas 진입");
			try {
				return (List<MonthlyProductSalesStatsDTO>)jdbcTemplate.query(SELECTALL_ADMIN_STAT_DATA, args, new SelectAdminMonthlyProductSalesStatsRowMapper());
			}
			catch (Exception e) {

				log.error("selectAdminStatProductDatas 예외/실패 ");

				return null;
			}
		}
		log.error("selectAll 실패");
		return null;
	}

	
	public MonthlyProductSalesStatsDTO selectOne(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

//		Object[] args = { monthlyProductSalesStatsDTO.getMonthlyProductSalesStatsID() };
//		log.trace("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new MonthlyProductSalesStatsRowMapper());
//		} catch (Exception e) {
//			log.error("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.error("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.error("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		
//		int result = jdbcTemplate.update(DELETE);
//		if(result <= 0) {
//			log.error("delete 실패");
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.setMonthlyProductSalesStatsID(rs.getInt("MONTHLY_PRODUCT_SALES_STATS_ID"));
		data.setProductID(rs.getInt("PRODUCT_ID"));
		data.setMonthlyTotalCalculateDate(rs.getDate("MONTHLY_TOTAL_CALCULATE_DATE"));
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

@Slf4j
class SelectAdminMonthlyProductSalesStatsRowMapper implements RowMapper<MonthlyProductSalesStatsDTO> {
	@Override
	public MonthlyProductSalesStatsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonthlyProductSalesStatsDTO data = new MonthlyProductSalesStatsDTO();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		data.setProductID(rs.getInt("MPS.PRODUCT_ID"));
		data.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		data.setMonthlyTotalCalculateDate(rs.getDate("MPS.MONTHLY_TOTAL_CALCULATE_DATE"));
		data.setMonthlyTotalQuantity(rs.getInt("MPS.MONTHLY_TOTAL_QUANTITY"));
		data.setMonthlyTotalGrossMargine(rs.getInt("MPS.MONTHLY_TOTAL_GROSS_MARGINE"));
		data.setMonthlyTotalNetProfit(rs.getInt("MPS.MONTHLY_TOTAL_NET_PROFIT"));
			
		log.debug("MPS.PRODUCT_ID : " + Integer.toString(rs.getInt("DPS.PRODUCT_ID")));
		log.debug("PRODUCT_NAME : " + rs.getString("P.PRODUCT_NAME"));
		log.debug("MPS.MONTHLY_TOTAL_CALCULATE_DATE : " + sdf.format(rs.getDate("MPS.MONTHLY_TOTAL_CALCULATE_DATE")));
		log.debug("MONTHLY_TOTAL_QUANTITY : " + Integer.toString(rs.getInt("MPS.MONTHLY_TOTAL_QUANTITY")));
		log.debug("MONTHLY_TOTAL_GROSS_MARGINE : " + Integer.toString(rs.getInt("MPS.MONTHLY_TOTAL_GROSS_MARGINE")));
		log.debug("MONTHLY_TOTAL_NET_PROFIT : " + Integer.toString(rs.getInt("MPS.MONTHLY_TOTAL_NET_PROFIT")));
		
		
		return data;
	}
}
