package com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel;

import java.sql.Date;

import lombok.Data;

@Data
public class MonthlyProductSalesStatsDTO {
	
	private int monthlyProductSalesStatsID; 	// 월별 상품 통계 아이디
	private int productID; 						// 상품 아이디
	private Date monthlyTotalCalculateDate;		// 통계월
	private int monthlyTotalQuantity; 			// 총 수량
	private int monthlyTotalGrossMargine; 		// 매출
	private int monthlyTotalNetProfit; 			// 이익 = 매출 - 원가
	
	private String searchCondition; 			// 쿼리 분기
	
												// FK
	private String ancProductName;				// 상품명
	private Date ancStartDate;					// 검색 기간 시작일
	private Date ancEndDate;					// 검색 기간 종료일
	private int ancStartRow;					// 통계 시작 행 번호
	private int ancSelectMax;					// 페이지별 출력할 row 수
}
