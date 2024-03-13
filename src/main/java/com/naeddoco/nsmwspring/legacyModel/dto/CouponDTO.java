package com.naeddoco.nsmwspring.legacyModel.dto;

import java.sql.Timestamp;

public class CouponDTO {
    private String CPID;
    private String MID;
    private String cpName;
    private Timestamp period;
    private int discount;
    private String used;
    private String category;
    private String searchCondition;
    private String ancPeriod;
	public String getCPID() {
		return CPID;
	}
	public void setCPID(String cPID) {
		CPID = cPID;
	}
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Timestamp getPeriod() {
		return period;
	}
	public void setPeriod(Timestamp period) {
		this.period = period;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getAncPeriod() {
		return ancPeriod;
	}
	public void setAncPeriod(String ancPeriod) {
		this.ancPeriod = ancPeriod;
	}
	@Override
	public String toString() {
		return "CouponDTO [CPID=" + CPID + ", MID=" + MID + ", cpName=" + cpName + ", period=" + period + ", discount="
				+ discount + ", used=" + used + ", category=" + category + ", searchCondition=" + searchCondition
				+ ", ancPeriod=" + ancPeriod + "]";
	}
    
    
   
	
}
