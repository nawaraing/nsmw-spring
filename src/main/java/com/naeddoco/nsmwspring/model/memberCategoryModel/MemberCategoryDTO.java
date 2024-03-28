package com.naeddoco.nsmwspring.model.memberCategoryModel;

import lombok.Data;

@Data
public class MemberCategoryDTO {
	private int memberCategoryID; 	// 회원 카테고리 아이디(인조식별자)
	private int categoryID;			// 카테고리 아이디
	private String memberID; 		// 회원 아이디
	
    private String searchCondition; // 쿼리 분기
    								
    								// FK
    private String ancCategoryName; // 카테고리 이름(카테고리테이블)

	
}
