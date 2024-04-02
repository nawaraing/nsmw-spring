package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberCouponDTO {
	private int memberCouponID;			// 멤버 쿠폰 아이디
	private String memberID;			// 회원 아이디
	private int couponID;				// 쿠폰 아이디
	private String couponUsage;			// 사용 여부
	
	private String searchCondition; 	// 쿼리 분기 지정
	
										// FK
	private String ancCouponName; 		// 쿠폰명
	private Timestamp ancExpirationDate;// 쿠폰 만료일
	private String ancCouponType; 		// 쿠폰 종류
	private int ancDiscount;			// 할인액 or 할인율 
	private int ancAmount;				// 최소 구매 금액 or 최대 할인 금액
	
}
