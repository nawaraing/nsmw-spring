package com.naeddoco.nsmwspring.model.memberModel;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberDTO {
	
	private String memberID; 	   				 // 아이디
	private int gradeID; 		   				 // 등급 아이디
	private String memberPassword; 				 // 비밀번호
	private String memberName; 	   				 // 이름
	private Date dayOfBirth; 	   				 // 생년월일
	private String gender; 		   				 // 성별
	private String phoneNumber;    				 // 전화번호
	private String email; 		   				 // 이메일
	private String authority; 	   				 // 권한(ADMIN or USER)
	private String memberState;    				 // 회원 탈퇴 여부(가입, 탈퇴 등)
	
	private String searchCondition; 			 // 쿼리 분기 지정
			
	private int ancShippingDefault;          	 // 배송지 출력 우선순위
	private int ancShippingPostCode;      	 	 // 배송지 우편번호
	private String ancShippingAddress;       	 // 배송지 주소
	private String ancShippingAddressDetail; 	 // 배송지 상세
	
	private int ancSubcriptionInfoID;            // 구독 정보 아이디
	private Timestamp ancBeginDate;              // 구독 시작일
	private int ancSubscriptionTimes;            // 구독 회차
	private Timestamp ancNextPaymentDate;        // 다음 결제일
	private int ancSubscriptionPostcode;         // 배송지 우편번호
	private String ancSubscriptionAddress;       // 배송지 주소
	private String ancSubscriptionDetailAddress; // 배송지 상세주소
	private int ancSubscriptionClosingTimes;     // 구독 마감 회차
	
	private int ancSubscriptionInfoProductID;    // 구독 정보 상품 아이디
	private int ancSubscriptionInfoID;           // 구독 정보 아이디
	private int ancProductID;                    // 상품 아이디
	private int ancQuantity;                     // 구독 수량
	private int ancPurchasePrice;                // 구독 시점 개당 가격
	
	private String ancCategoryName;				// 카테고리 이름
	
	@Override
	public String toString() {
		
		return "MemberDTO ["
			    + "memberID = " + memberID + ", "
				+ "gradeID = " + gradeID + ", "
				+ "memberPassword = " + memberPassword + ", " 
				+ "memberName = " + memberName + ", " 
				+ "dayOfBirth = " + dayOfBirth + ", "
				+ "gender = " + gender + ", " 
				+ "phoneNumber = " + phoneNumber + ", "
				+ "email = " + email + ", "
				+ "authority = " + authority + "," 
				+ "ancShippingDefault = " + ancShippingDefault + ", "
				+ "ancShippingPostCode = " + ancShippingPostCode + ", "
				+ "ancShippingAddress = " + ancShippingAddress +", "
				+ "ancShippingAddressDetail = " + ancShippingAddressDetail + ", " 
				+ "ancSubcriptionInfoID = " + ancSubcriptionInfoID + ", "
				+ "ancBeginDate = " + ancBeginDate + ", "
				+ "ancSubscriptionTimes = " + ancSubscriptionTimes + ", "
				+ "ancNextPaymentDate = " + ancNextPaymentDate + ", "
				+ "ancSubscriptionPostcode = " + ancSubscriptionPostcode + ", "
				+ "ancSubscriptionAddress = " + ancSubscriptionAddress + ", "
				+ "ancSubscriptionDetailAddress = " + ancSubscriptionDetailAddress + ", "
				+ "ancSubscriptionClosingTimes = " + ancSubscriptionClosingTimes + ", "
				+ "ancSubscriptionInfoProductID = " + ancSubscriptionInfoProductID + ", "
				+ "ancSubscriptionInfoID = " + ancSubscriptionInfoID + ", "
				+ "ancProductID = " + ancProductID + ", "
				+ "ancQuantity = " + ancQuantity + ", "
				+ "ancPurchasePrice = " + ancPurchasePrice				
				+ "]";
		
	}
 	
}