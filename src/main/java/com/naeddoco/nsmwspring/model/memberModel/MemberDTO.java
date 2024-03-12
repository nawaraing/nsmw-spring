package com.naeddoco.nsmwspring.model.memberModel;

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
	
	public String getMemberID() {
		return memberID;
	}
	
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	public String getMemberPassword() {
		return memberPassword;
	}
	
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	
	public String getMemberName() {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public String getDayOfBirth() {
		return dayOfBirth;
	}
	
	public void setDayOfBirth(String dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getMemberState() {
		return memberState;
	}
	
	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}
	
}