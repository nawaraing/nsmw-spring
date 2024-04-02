package com.naeddoco.nsmwspring.model.couponCategoryModel;

import lombok.Data;

@Data
public class CouponCategoryDTO {
	private int couponCategoryID; 	// 쿠폰 카테고리 아이디(인조식별자)
	private int couponID; 			// 쿠폰 아이디
	private int categoryID; 		// 카테고리 아이디
	
    private String searchCondition; // 쿼리 분기 지정
    
}
