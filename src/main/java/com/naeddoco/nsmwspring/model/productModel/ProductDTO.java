package com.naeddoco.nsmwspring.model.productModel;

import lombok.Data;

@Data
public class ProductDTO {
	
	private int productID; // 인조식별자 번호
	private String productName; // 이름
	private String productDetail; // 상세
	private int costPrice; // 원가
	private int retailPrice; // 소비자가
	private int salePrice; // 판매가
	private int stock; // 재고
	private String ingredient; // 성분
	private String usage; // 용법
	private String expirationDate; // 소비기한
	private String registerDate; // 등록일
	private String modifyDate; // 변경일 
	private String saleState; // 판매 상태
	
	private String searchCondition; // 쿼리 분기
	
	private String ancImagePath; // 이미지
	private String ancCategory;
	
	@Override
	public String toString() {
		
		return "ProductDTO [productID =" + productID + ", "
				+ "productName =" + productName + ", "
				+ "productDetail =" + productDetail + ", " 
				+ "costPrice =" + costPrice + ", " 
				+ "retailPrice =" + retailPrice + ", "
				+ "salePrice =" + salePrice + ", " 
				+ "stock =" + stock + ", "
				+ "ingredient =" + ingredient + ", "
				+ "usage =" + usage + "," 
				+ "expirationDate =" + expirationDate + ", "
				+ "registerDate =" + registerDate + ", "
				+ "modifyDate =" + modifyDate +", "
				+ "saleState =" + saleState 
				+ "]";
		
	}
	
}
