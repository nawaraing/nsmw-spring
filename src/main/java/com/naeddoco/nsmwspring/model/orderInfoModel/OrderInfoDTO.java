package com.naeddoco.nsmwspring.model.orderInfoModel;

import lombok.Data;

@Data
public class OrderInfoDTO {
	
	private int orderInfoID; // 인조 식별자
	private int buyInfoID; // 구매 내역 아이디
	private int productID; // 상품 아이디
	private int memberCouponID; // 사용자 쿠폰 아이디
	private int buyQuantity; // 구매 수량
	private int paymentPrice; // 결제 금액
	private int hasReview; // 리뷰 여부
	
	private String searchCondition;
	
	private String ancProductName;
	private String ancProductDetail;
	private String ancCategoryName;
	private String ancImagePath;
	
	@Override
	public String toString() {
		
		return "OrderDTO ["
			    + "orderInfoID = " + orderInfoID + ", "
				+ "buyInfoID = " + buyInfoID + ", "
				+ "productID = " + productID + ", " 
				+ "memberCouponID = " + memberCouponID + ", " 
				+ "buyQuantity = " + buyQuantity + ", "
				+ "paymentPrice = " + paymentPrice + ", " 
				+ "hasReview = " + hasReview + ", "
				+ "ancProductName = " + ancProductName + ", "
				+ "ancProductDetail = " + ancProductDetail + ", "
				+ "ancCategoryName = " + ancCategoryName + ", "
				+ "ancImagePath = " + ancImagePath
				+ "]";
		
	}

}
