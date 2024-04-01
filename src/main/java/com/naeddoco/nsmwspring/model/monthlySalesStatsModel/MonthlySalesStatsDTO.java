package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.sql.Date;
import lombok.Data;

@Data
public class MonthlySalesStatsDTO {
	private int monthlySalesStatsID;			// 월 매출 아이디
	private Date monthlyTotalCalculateDate;		// 통계월
	private int monthlyTotalGrossMargine;		// 매출
	private int monthlyTotalNetProfit;			// 이익 = 매출 - 원가
	
	private String searchCondition; 			// 쿼리 분기
	
												// FK
	private Date ancStartMonth;				// 검색 기간 시작월
	private Date ancEndMonth;					// 검색 기간 마지막월
	
}
