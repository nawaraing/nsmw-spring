package com.naeddoco.nsmwspring.model.orderInfoModel;

import lombok.Data;

@Data
public class OrderInfoDTO {
	
	private int orderInfoID;
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
			    + "orderInfoID = " + orderInfoID + ", "
				+ "buyInfoID = " + buyInfoID + ", "
				+ "productID = " + productID + ", " 
				+ "memberCouponID = " + memberCouponID + ", " 
				+ "buyQuantity = " + buyQuantity + ", "
				+ "paymentPrice = " + paymentPrice + ", " 
				+ "hasReview = " + hasReview
				+ "]";
		
	}

}
