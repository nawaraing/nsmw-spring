package com.naeddoco.nsmwspring.model.memberModel;

import lombok.Data;

@Data
public class MemberDTO {
	
	private String memberID; // 아이디
	private String memberPassword;// 비밀번호
	private String memberName;// 이름
	private String dayOfBirth;// 생년월일
	private String gender;// 성별
	private String phoneNumber;// 전화버호
	private String email;// 이메일
	private String authority;// 권한(admin or user)
	private String memberState; // 상태(탈퇴 or 고스트 등등)
	
	
	
}