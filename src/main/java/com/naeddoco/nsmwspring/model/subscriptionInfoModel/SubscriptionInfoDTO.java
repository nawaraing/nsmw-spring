package com.naeddoco.nsmwspring.model.subscriptionInfoModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SubscriptionInfoDTO {
	
	private int subscriptionInfoID; // 인조 식별자
	private String memberID; // 유저 아이디
	private Timestamp beginDate; // 구독 시작일
	private int subscriptionTimes; // 구독 회차
	private Timestamp nextPaymentDate; // 다음 결제일
	private int subscriptionPostCode; // 배송지 우편번호
	private String subscriptionAddress; // 배송지 주소
	private String subscriptionDetailAddress; // 배송지 주소 상세
	private int subscriptionClosingTimes; // 구독 마감 회차
	
	@Override
	public String toString() {
		
		return "SubscriptionInfoDTO [" + 
			   "subscriptionInfoID = " + subscriptionInfoID + ", " + 
			   "memberID = " + memberID + ", " +
			   "beginDate = " + beginDate + ", " +
			   "subscriptionTimes = " + subscriptionTimes + ", " +
			   "nextPaymentDate = " + nextPaymentDate + ", " +
			   "subscriptionPostCode = " + subscriptionPostCode + ", " +
			   "subscriptionAddress = " + subscriptionAddress + ", " +
			   "subscriptionDetailAddress = " + subscriptionDetailAddress + ", " +
			   "subscriptionClosingTimes = " + subscriptionClosingTimes +
			   "]";
				
	}

}
