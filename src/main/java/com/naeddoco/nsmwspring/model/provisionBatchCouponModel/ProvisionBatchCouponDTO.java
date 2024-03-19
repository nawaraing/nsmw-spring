package com.naeddoco.nsmwspring.model.provisionBatchCouponModel;

import lombok.Data;

@Data
public class ProvisionBatchCouponDTO {
	private int provisionBatchCouponID;	// 쿠폰 전체 발송 아이디
	private int CouponID;				// 쿠폰 아이디
	private String deployStatus;		// 배포 상태
	
	private String searchCondition; 	// 쿼리 분기
	
}
