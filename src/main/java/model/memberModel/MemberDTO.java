package model.memberModel;

public class MemberDTO {
	
	private String MEMBER_ID; // 아이디
	private String MEMBER_PASSWORD;// 비밀번호
	private String MEMBER_NAME;// 이름
	private String DAY_OF_BIRTH;// 생년월일
	private String GENDER;// 성별
	private String PHONE_NUMBER;// 전화버호
	private String EMAIL;// 이메일
	private String AUTHORITY;// 권한(admin or user)
	private String MEMBER_STATE; // 상태(탈퇴 or 고스트 등등)
	
	public String getMEMBER_ID() {
		return MEMBER_ID;
	}
	
	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}
	
	public String getMEMBER_PASSWORD() {
		return MEMBER_PASSWORD;
	}
	
	public void setMEMBER_PASSWORD(String mEMBER_PASSWORD) {
		MEMBER_PASSWORD = mEMBER_PASSWORD;
	}
	
	public String getMEMBER_NAME() {
		return MEMBER_NAME;
	}
	
	public void setMEMBER_NAME(String mEMBER_NAME) {
		MEMBER_NAME = mEMBER_NAME;
	}
	
	public String getDAY_OF_BIRTH() {
		return DAY_OF_BIRTH;
	}
	
	public void setDAY_OF_BIRTH(String dAY_OF_BIRTH) {
		DAY_OF_BIRTH = dAY_OF_BIRTH;
	}
	
	public String getGENDER() {
		return GENDER;
	}
	
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	
	public String getPHONE_NUMBER() {
		return PHONE_NUMBER;
	}
	
	public void setPHONE_NUMBER(String pHONE_NUMBER) {
		PHONE_NUMBER = pHONE_NUMBER;
	}
	
	public String getEMAIL() {
		return EMAIL;
	}
	
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	
	public String getAUTHORITY() {
		return AUTHORITY;
	}
	
	public void setAUTHORITY(String aUTHORITY) {
		AUTHORITY = aUTHORITY;
	}
	
	public String getMEMBER_STATE() {
		return MEMBER_STATE;
	}
	
	public void setMEMBER_STATE(String mEMBER_STATE) {
		MEMBER_STATE = mEMBER_STATE;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [MEMBER_ID =" + MEMBER_ID + 
			   ", MEMBER_PASSWORD =" + MEMBER_PASSWORD + 
			   ", MEMBER_NAME =" + MEMBER_NAME + 
			   ", DAY_OF_BIRTH =" + DAY_OF_BIRTH + 
			   ", GENDER =" + GENDER +
			   ", PHONE_NUMBER =" + PHONE_NUMBER +
			   ", EMAIL =" + EMAIL +
			   ", AUTHORITY =" + AUTHORITY +
			   ", MEMBER_STATE =" + MEMBER_STATE +
			   "]";
		
	}
	
}