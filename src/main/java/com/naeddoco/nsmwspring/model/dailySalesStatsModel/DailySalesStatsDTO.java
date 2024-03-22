package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.sql.Date;

import lombok.Data;

@Data
public class DailySalesStatsDTO {
	private int dailySalesStatsID; 				// 일 매출 아이디
	private Date dailyTotalCalculateDate; 		// 통계일
	private int dailyTotalGrossMargine;			// 매출
	private int dailyTotalNetProfit; 			// 이익 = 매출 - 원가
	
	private String searchCondition; 			// 쿼리 분기 지정
	
												// FK
	private Date ancStartDate;					// 검색 기간 시작일 
	private Date ancEndDate;					// 검색 기간 종료일
	private int ancStartRow;					// 통계 시작 행 번호
	private int ancSelectMax;					// 페이지별 출력할 row 수
	
}
