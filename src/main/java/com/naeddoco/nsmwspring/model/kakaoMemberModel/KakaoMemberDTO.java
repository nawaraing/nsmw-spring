package com.naeddoco.nsmwspring.model.kakaoMemberModel;

import lombok.Data;

@Data
public class KakaoMemberDTO {
	
	private String kakaoID; //카카오 아이디
	private String MemberID; // 회원 아이디
	
	private String searchCondition; // 쿼리 분기 지정
}
