package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BuyInfoDTO {
	
	private int buyInfoID; 					// 구매 내역 아이디
	private String memberID; 				// 회원 아이디
	private Timestamp buyDate; 				// 구매일
	private int deliveryPostcode; 			// 배송지 우편번호
	private String deliveryAddress; 		// 배송지 주소
	private String deliveryDetailAddress; 	// 배송지 상세주소
	private String orderState; 				// 주문상태
	
    private String searchCondition; 		// 쿼리 분기 지정
    
    @Override
	public String toString() {
		
		return "BuyInfoDTO ["
			    + "buyInfoID = " + buyInfoID + ", "
				+ "memberID = " + memberID + ", "
				+ "buyDate = " + buyDate + ", " 
				+ "deliveryPostcode = " + deliveryPostcode + ", " 
				+ "deliveryAddress = " + deliveryAddress + ", "
				+ "deliveryDetailAddress = " + deliveryDetailAddress + ", " 
				+ "orderState = " + orderState
				+ "]";
		
	}
    
}