package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MonthlySalesStatsDTO {
	private int monthlySalesStatsID;			// 월 매출 아이디
	private Timestamp monthlyTotalCalculateDate;// 통계월
	private int monthlyTotalGrossMargine;		// 매출
	private int monthlyTotalNetProfit;			// 이익 = 매출 - 원가
	
}
