package com.naeddoco.nsmwspring.model.cartModel;

import lombok.Data;

@Data
public class CartDTO {

	private int cartID; 			// 장바구니 아이디
	private String memberID; 		// 회원 아이디
	private int productID; 			// 상품 아이디
	private int productQuantity; 	// 담긴 수량
	
    private String searchCondition; // 쿼리 분기 지정
    
    								// FK
    private String ancProductName;	// 상품명
    private int ancSalePrice;		// 판매 가격
    private String ancImagePath;	// 이미지 경로
    
}
