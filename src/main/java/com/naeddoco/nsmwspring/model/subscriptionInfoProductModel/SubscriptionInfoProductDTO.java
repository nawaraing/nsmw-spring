package com.naeddoco.nsmwspring.model.subscriptionInfoProductModel;

import lombok.Data;

@Data
public class SubscriptionInfoProductDTO {
	
	private int subscriptionInfoProductID; // 인조 식별자
	private int subscriptionInfoID; // 구독 정보 PK
	private int productID; // 상품 PK
	private int quantity; // 수량
	private int purchasePrice; // 구매 금액
	
	@Override
	public String toString() {
		
		return "SubscriptionInfoProductDTO [" + 
			   "subscriptionInfoProductID = " + subscriptionInfoProductID + ", " + 
			   "subscriptionInfoID = " + subscriptionInfoID + ", " +
			   "productID = " + productID + ", " +
			   "quantity = " + quantity + ", " +
			   "purchasePrice = " + purchasePrice +
			   "]";
				
	}

}
