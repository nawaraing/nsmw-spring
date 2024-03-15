package com.naeddoco.nsmwspring.model.memberModel;

import lombok.Data;

@Data
public class MemberDTO {
	
	private String memberID; // 아이디
	private String memberPassword; // 비밀번호
	private String memberName; // 이름
	private String dayOfBirth; // 생년월일
	private String gender; // 성별
	private String phoneNumber; // 전화버호
	private String email; // 이메일
	private String authority; // 권한(ADMIN or USER)
	private String memberState; // 회원 탈퇴 여부(가입, 탈퇴 등등)
	
	private String searchCondition; // 쿼리 분기 지정
	
	private int ancGradeID; // FK : 회원 등급 아이디
	
}