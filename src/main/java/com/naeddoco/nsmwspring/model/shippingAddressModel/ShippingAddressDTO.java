package com.naeddoco.nsmwspring.model.shippingAddressModel;

import lombok.Data;

@Data
public class ShippingAddressDTO {

	private int shippingAddressID;			// 배송지 아이디
	private String memberID;				// 회원 아이디
	private int shippingDefault;			// 기본 배송지
	private String shippingAddressName;		// 배송지 이름
	private int shippingPostcode;			// 배송지 우편번호(범위: 01000~63999)
	private String shippingAddress;			// 배송지 주소
	private String shippingDetailAddress;	// 배송지 상세주소
	
	private String searchCondition;			// 쿼리 분기
	
}
