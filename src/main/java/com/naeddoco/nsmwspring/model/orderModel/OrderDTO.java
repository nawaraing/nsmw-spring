package com.naeddoco.nsmwspring.model.orderModel;

import lombok.Data;

@Data
public class OrderDTO {
	
	private int orderID;
	private int buyInfoID;
	private int productID;
	private int memberCouponID;
	private int buyQuantity;
	private int paymentPrice;
	private int hasReview;
	
	private String searchCondition;
	
	@Override
	public String toString() {
		
		return "OrderDTO ["
			    + "orderID = " + orderID + ", "
				+ "buyInfoID = " + buyInfoID + ", "
				+ "productID = " + productID + ", " 
				+ "memberCouponID = " + memberCouponID + ", " 
				+ "buyQuantity = " + buyQuantity + ", "
				+ "paymentPrice = " + paymentPrice + ", " 
				+ "hasReview = " + hasReview
				+ "]";
		
	}

}
