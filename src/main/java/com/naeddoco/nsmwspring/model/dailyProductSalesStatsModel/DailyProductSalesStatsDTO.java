package com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel;

import java.sql.Date;
import lombok.Data;

@Data
public class DailyProductSalesStatsDTO {
	
	private int dailyProductSalesStatsID; 		// 일별 상품 통계 아이디
	private int productID;						// 상품 아이디
	private Date dailyTotalCalculateDate; 		// 통계일
	private int dailyTotalQuantity; 			// 총 수량
	private int dailyTotalGrossMargine; 		// 매출
	private int dailyTotalNetProfit; 			// 이익

    private String searchCondition; 			// 쿼리 분기
    
    											// FK
    private String ancProductName;				// 상품명
    private Date ancStartDate;					// 검색 기간 시작일
    private Date ancEndDate;					// 검색 기간 종료일

    
}
