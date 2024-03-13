package com.naeddoco.nsmwspring.legacyModel.dto;

import java.sql.Date;

public class MemberDTO {

	private String MID;
	private String mName;
	private String mPassword;
	private Date dob;
	private String gender;
	private String phoneNumber;
	private String email;
	private int mPostCode;
	private String mAddress;
	private String mDetailedAddress;
	private String grade;
	private String health;
	private String searchCondition;
	private String loginType;
	private String kakaoId;
	
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmPassword() {
		return mPassword;
	}
	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
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
	public int getmPostCode() {
		return mPostCode;
	}
	public void setmPostCode(int mPostCode) {
		this.mPostCode = mPostCode;
	}
	public String getmAddress() {
		return mAddress;
	}
	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	public String getmDetailedAddress() {
		return mDetailedAddress;
	}
	public void setmDetailedAddress(String mDetailedAddress) {
		this.mDetailedAddress = mDetailedAddress;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [MID=" + MID + ", mName=" + mName + ", mPassword=" + mPassword + ", dob=" + dob + ", gender="
				+ gender + ", phoneNumber=" + phoneNumber + ", email=" + email + ", mPostCode=" + mPostCode
				+ ", mAddress=" + mAddress + ", mDetailedAddress=" + mDetailedAddress + ", grade=" + grade + ", health="
				+ health + ", searchCondition=" + searchCondition + ", loginType=" + loginType + ", kakaoId=" + kakaoId
				+ "]";
	}
}
