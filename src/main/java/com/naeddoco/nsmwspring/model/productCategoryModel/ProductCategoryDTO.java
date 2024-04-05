package com.naeddoco.nsmwspring.model.productCategoryModel;

import lombok.Data;

@Data
public class ProductCategoryDTO {
	
	private int productCategoryID;   // 인조 식별자
	private int productID;           // 상품 PK
	private int categoryID;          // 카테고리 PK
	
	private String searchCondition;  // 쿼리 분기
	
	private String ancProductName;   // 상품 이름
	private String ancProductDetail; // 상품 상세
	private int ancSalePrice;        // 상품 판매가
	private String ancImagePath;     // 상품 이미지 경로
	private String ancCategoryName;  // 카테고리 이름
	
	private int ancLimit;            // SELECTALL LIMIT ?
	
	@Override
	public String toString() {
		
		return "ProductCategoryDTO [" + "productCategoryID = " + productCategoryID + ", " 
									  + "productID = " + productID + ", " 
				                      + "categoryID = " + categoryID + ", " 
				                      + "ancImagePath = " + ancImagePath + ", " 
				                      + "ancCategoryName = " + ancCategoryName + ", " 
				                      + "ancLimit = " + ancLimit
				                      + "]";
				
	}
	
}
