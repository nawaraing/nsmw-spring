package com.naeddoco.nsmwspring.model.couponModel;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CouponDTO {
	private int couponID; // 쿠폰 아이디
	private String couponName; // 쿠폰명
	private Timestamp createDate; // 쿠폰 생성일
	private Timestamp distributeDate; //쿠폰 배포일
	private Timestamp expirationDate; //쿠폰 만료일
	private String couponType; // 쿠폰 종류
	
    private String searchCondition; // 쿼리 분기 지정
}
