package com.naeddoco.nsmwspring.model.productImageModel;

import lombok.Data;

@Data
public class ProductImageDTO {
	
	private int productImageID; // 인조 식별자
	private int imageID; // 이미지 아이디
	private int productID; // 상품 아이디
	
	@Override
	public String toString() {
		
		return "ProductImageDTO ["
			    + "productImageID = " + productImageID + ", "
			    + "imageID = " + imageID + ", "
				+ "productID = " + productID
				+ "]";
		
	}

}
