package com.naeddoco.nsmwspring.model.provisionGradeCouponModel;

import lombok.Data;

@Data
public class ProvisionGradeCouponDTO {

	private int provisionGradeCouponID;		// 등급별 쿠폰 아이디
	private int gradeID;					// 등급 아이디
	private int couponID;					// 쿠폰 아이디
	private String deployCycle;				// 지급 주기
	private String deployBase;				// 지급 기준
	
	private String searchCondition; 		// 쿼리 분기
}
