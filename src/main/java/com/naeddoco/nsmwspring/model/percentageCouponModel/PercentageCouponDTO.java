package com.naeddoco.nsmwspring.model.percentageCouponModel;

import lombok.Data;

@Data
public class PercentageCouponDTO {
	private int percentageCouponID; // 퍼센트 쿠폰 타입 아이디
	private int couponID;			// 쿠폰 아이디
	private int couponDiscountRate;	// 할인율
	private int maxDiscountAmount;	// 최대 할인 금액
	
	private String searchCondition; // 쿼리 분기
	
}
