package com.naeddoco.nsmwspring.model.wonCoupon;

import lombok.Data;

@Data
public class WonCouponDTO {
	private int wonCouponID;			// 원화 쿠폰 타입 아이디
	private int couponID;				// 쿠폰 아이디
	private int couponDiscountAmount; 	// 할인액
	private int minOrderAmount;			// 최소 구매 금액
	
	private String searchCondition; 	// 쿼리 분기
	
}
