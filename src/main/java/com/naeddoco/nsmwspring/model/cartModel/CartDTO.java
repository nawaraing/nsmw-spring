package com.naeddoco.nsmwspring.model.cartModel;

import lombok.Data;

@Data
public class CartDTO {

	private int cartID; // 장바구니 아이디
	private String memberID; // 회원 아이디
	private int productID; // 상품 아이디
	private int productQuantity; //담기 수량
	
    private String searchCondition; // 쿼리 분기 지정
}
