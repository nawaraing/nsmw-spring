package com.naeddoco.nsmwspring.legacyModel.dto;

public class CartDTO {
	
	private int CID;
	private String MID;
	private int PID;
	private int cQty;
	private String searchCondition;
    // 컬럼에 없는 멤버변수
	private String ancPName;
	private int ancSellingPrice;
	private String ancImagePath;
	
	
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public int getPID() {
		return PID;
	}
	public void setPID(int pID) {
		PID = pID;
	}
	public int getcQty() {
		return cQty;
	}
	public void setcQty(int cQty) {
		this.cQty = cQty;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getAncPName() {
		return ancPName;
	}
	public void setAncPName(String ancPName) {
		this.ancPName = ancPName;
	}
	public int getAncSellingPrice() {
		return ancSellingPrice;
	}
	public void setAncSellingPrice(int ancSellingPrice) {
		this.ancSellingPrice = ancSellingPrice;
	}
	public String getAncImagePath() {
		return ancImagePath;
	}
	public void setAncImagePath(String ancImagePath) {
		this.ancImagePath = ancImagePath;
	}
	@Override
	public String toString() {
		return "CartDTO [CID=" + CID + ", MID=" + MID + ", PID=" + PID + ", cQty=" + cQty + ", searchCondition="
				+ searchCondition + ", ancPName=" + ancPName + ", ancSellingPrice=" + ancSellingPrice
				+ ", ancImagePath=" + ancImagePath + "]";
	}
}
