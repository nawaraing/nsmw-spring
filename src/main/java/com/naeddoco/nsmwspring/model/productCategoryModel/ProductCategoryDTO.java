package com.naeddoco.nsmwspring.model.productCategoryModel;

import lombok.Data;

@Data
public class ProductCategoryDTO {
	
	private int productCategoryID; // 인조 식별자
	private int productID; // 상품 PK
	private int categoryID; // 카테고리 PK
	
	private String searchCondition; // 쿼리 분기
	
	@Override
	public String toString() {
		
		return "ProductCategoryDTO [" + "productCategoryID = " + productCategoryID + ", " 
									  + "productID = " + productID + ", " 
				                      + "categoryID = " + categoryID 
				                      + "]";
				
	}
	
}
