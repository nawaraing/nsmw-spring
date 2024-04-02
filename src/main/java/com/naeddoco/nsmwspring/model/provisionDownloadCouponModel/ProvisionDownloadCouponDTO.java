package com.naeddoco.nsmwspring.model.provisionDownloadCouponModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProvisionDownloadCouponDTO {

	private int provisionDownloadCouponID;	// 쿠폰 다운로드 아이디
	private int couponID;					// 쿠폰 아이디
	private int imageID;					// 이미지 아이디
	private Timestamp deployDeadline;		// 이미지 아이디
	private String deployStatus;			// 배포 상태
	
	private String searchCondition; 		// 쿼리 분기
											
											// FK
	private String ancCouponName; 			// 쿠폰명
	private Timestamp ancCreateDate; 		// 쿠폰 생성일
	private Timestamp ancDistributeDate; 	// 쿠폰 배포일
	private Timestamp ancExpirationDate;	// 쿠폰 만료일
	private String ancCouponType; 			// 쿠폰 종류
	private int ancDiscount;				// 할인액 or 할인율 
	private int ancAmount;					// 최소 구매 금액 or 최대 할인 금액
	private String ancCategoryName;			// 카테고리명
	private int ancImageID; 				// 인조 식별자
	private String ancImagePath; 			// 이미지 경로
	
}
