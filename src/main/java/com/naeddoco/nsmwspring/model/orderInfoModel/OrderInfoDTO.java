package com.naeddoco.nsmwspring.model.orderInfoModel;

import lombok.Data;

@Data
public class OrderInfoDTO {
	
	private int orderInfoID;          // 인조 식별자
	private int buyInfoID;            // 구매 내역 아이디
	private int productID;            // 상품 아이디
	private int memberCouponID;       // 사용자 쿠폰 아이디
	private int buyQuantity;          // 구매 수량
	private int paymentPrice;         // 결제 금액
	private int hasReview;            // 리뷰 여부
	
	private String searchCondition;   // 쿼리 분기
	
	private String ancProductName;    // 상품 이름
	private String ancProductDetail;  // 상품 상세
	private String ancSalePrice;      // 상품 가격
	private String ancCategoryName;   // 상품 카테고리 이름
	private String ancImagePath;      // 상품 이미지 경로
	
	private String ancMemberID;       // 회원 아이디 
	
	private int ancProductID;
	
	@Override
	public String toString() {
		
		return "OrderInfoDTO ["
			    + "orderInfoID = " + orderInfoID + ", "
				+ "buyInfoID = " + buyInfoID + ", "
				+ "productID = " + productID + ", " 
				+ "memberCouponID = " + memberCouponID + ", " 
				+ "buyQuantity = " + buyQuantity + ", "
				+ "paymentPrice = " + paymentPrice + ", " 
				+ "hasReview = " + hasReview + ", "
				+ "ancProductName = " + ancProductName + ", "
				+ "ancProductDetail = " + ancProductDetail + ", "
				+ "ancSalePrice = " + ancSalePrice + ", "
				+ "ancCategoryName = " + ancCategoryName + ", "
				+ "ancImagePath = " + ancImagePath + ", "
				+ "ancMemberID = " + ancMemberID
				+ "]";
		
	}

}
