package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BuyInfoDTO {
	
	private int buyInfoID; 					// 구매 내역 아이디
	private String memberID; 				// 회원 아이디
	private int subscriptionInfoID;         // 구독 정보 아이디
	private Timestamp buyDate; 				// 구매일
	private int deliveryPostcode; 			// 배송지 우편번호
	private String deliveryAddress; 		// 배송지 주소
	private String deliveryDetailAddress; 	// 배송지 상세주소
	private String orderState; 				// 주문상태
	
    private String searchCondition; 		// 쿼리 분기 지정
    
    private int maxPk;                      // 가장 높은 PK 
    
    private int ancProductID;               // 상품 아이디
    private String ancProductName;          // 상품 이름
    private String ancProductDetail;        // 상품 상세
    private int ancSalePrice;               // 판매 가격
    private String ancCategoryName;         // 상품 카테고리 이름
    private String ancImagePath;            // 상품 이미지 경로
    
    @Override
	public String toString() {
		
		return "BuyInfoDTO [" +
			   "buyInfoID = " + buyInfoID + ", " +
			   "memberID = " + memberID + ", " +
			   "subscriptionInfoID = " + subscriptionInfoID + ", " +
			   "buyDate = " + buyDate + ", " +
			   "deliveryPostcode = " + deliveryPostcode + ", " +
			   "deliveryAddress = " + deliveryAddress + ", " +
			   "deliveryDetailAddress = " + deliveryDetailAddress + ", " +
			   "orderState = " + orderState + ", " +
			   "maxPk = " + maxPk + ", " + ", " +
			   "ancProductID = " + ancProductID + ", " +
			   "ancProductName = " + ancProductName + ", " +
			   "ancProductDetail = " + ancProductDetail + ", " +
			   "ancSalePrice = " + ancSalePrice + ", " +
			   "ancCategoryName = " + ancCategoryName + ", " +
			   "ancImagePath = " + ancImagePath +
			   "]";
		
	}
    
}