package com.naeddoco.nsmwspring.model.couponCategoryModel;

import lombok.Data;

@Data
public class CouponCategoryDTO {
	private int CouponCategoryID; //쿠폰 카테고리 아이디(인조식별자)
	private int CouponID; // 쿠폰 아이디
	private int CategoryID; // 카테고리 아이디
	
    private String searchCondition; // 쿼리 분기 지정
}
