package com.naeddoco.nsmwspring.model.couponModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CouponDTO {
	
	private int couponID; 						// 쿠폰 아이디
	private String couponName; 					// 쿠폰명
	private Timestamp createDate;				// 쿠폰 생성일
	private Timestamp distributeDate; 			// 쿠폰 배포일
	private Timestamp expirationDate; 			// 쿠폰 만료일
	private String couponType; 					// 쿠폰 종류
	
    private String searchCondition; 			// 쿼리 분기 지정
    
    											// FK
    private String ancCategoryName;     		// 쿠폰 카테고리 
    private int ancDiscountAmount;      		// 원쿠폰 할인액
    private int ancMinOrderAmount;      		// 원쿠폰 최소 구매 금액
    private int ancDiscountRate;        		// 퍼센트쿠폰 할인율
    private int ancMaxDiscountAmount;   		// 퍼센트쿠폰 최대 할인 금액
    
	private String ancDeployCycle;				// 지급 주기
	private String ancDeployBase;				// 지급 기준
	
	private int ancDiscount;					// 할인액 or 할인율 
	private int ancAmount;						// 최소 구매 금액 or 최대 할인 금액
	
	private String ancGradeName;				// 등급 이름
	
	private String ancSearchKeyword;			// 검색 키워드
	private String ancSortColumnName;			// 정렬 키워드

	private String ancDeployStatus;				// 배포 상태
	private Timestamp ancDeployDeadline;		// 이미지 아이디
	
	private int ancProvisionDownloadCouponID;	// 쿠폰 다운로드 아이디
	private int ancImageID;						// 이미지 아이디
	private String ancImagePath; 				// 이미지 경로
	
	private int ancCouponCategoryID; 			// 쿠폰 카테고리 아이디
	private int ancCategoryID; 					// 카테고리 아이디
	private int ancPercentageCouponID; 			// 퍼센트 쿠폰 타입 아이디
	private int ancWonCouponID;					// 원화 쿠폰 타입 아이디
	
	
	
    @Override
   	public String toString() {
   		
   		return "CouponDTO [" + 
   			   "couponID = " + couponID + ", " + 
   			   "couponName = " + couponName + ", " +
   			   "createDate = " + createDate + ", " +
   			   "distributeDate = " + distributeDate + ", " +
   			   "expirationDate = " + expirationDate + ", " +
   			   "couponType = " + couponType + ", " +
   			   "ancCategoryName = " + ancCategoryName + ", " +
   			   "ancDiscountAmount = " + ancDiscountAmount + ", " +
   			   "ancMinOrderAmount = " + ancMinOrderAmount + ", " +
   			   "ancDiscountRate = " + ancDiscountRate + ", " +
   			   "ancMaxDiscountAmount = " + ancMaxDiscountAmount +
   			   "]";
   				
   	}
    
}