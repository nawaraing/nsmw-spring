package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DailySalesStatsDTO {
	private int dailySalesStatsID; 				// 일 매출 아이디
	private Timestamp dailyTotalCalculateDate; 	// 통계일
	private int dailyTotalGrossMargine;			// 매출
	private int dailyTotalNetProfit; 			// 이익 = 매출 - 원가
	
	private String searchCondition; 			// 쿼리 분기 지정
}
