package com.naeddoco.nsmwspring.legacyModel.dto;

import java.sql.Timestamp;

public class ProductDTO {
	
    private int PID;
    private String pName;
    private String pDetail;
    private int costPrice;
    private int regularPrice;
    private int sellingPrice;
    private int pQty;
    private String ingredient;
    private String usage;
    private String exp;
    private String category;
    private Timestamp regTime;
    private String sellingState;
    private String imagePath;
    private String searchCondition;
    // 컬럼에 없는 멤버변수
    private int ancSelectMin;
    private int ancSelectMax;
    private int ancTotalQty;
    private int ancCID;
    private String ancRegTime;
    private int ancAvgRating;
    
	public int getPID() {
		return PID;
	}
	public void setPID(int pID) {
		PID = pID;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpDetail() {
		return pDetail;
	}
	public void setpDetail(String pDetail) {
		this.pDetail = pDetail;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(int regularPrice) {
		this.regularPrice = regularPrice;
	}
	public int getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public int getpQty() {
		return pQty;
	}
	public void setpQty(int pQty) {
		this.pQty = pQty;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Timestamp getRegTime() {
		return regTime;
	}
	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}
	public String getSellingState() {
		return sellingState;
	}
	public void setSellingState(String sellingState) {
		this.sellingState = sellingState;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public int getAncSelectMin() {
		return ancSelectMin;
	}
	public void setAncSelectMin(int ancSelectMin) {
		this.ancSelectMin = ancSelectMin;
	}
	public int getAncSelectMax() {
		return ancSelectMax;
	}
	public void setAncSelectMax(int ancSelectMax) {
		this.ancSelectMax = ancSelectMax;
	}
	public int getAncTotalQty() {
		return ancTotalQty;
	}
	public void setAncTotalQty(int ancTotalQty) {
		this.ancTotalQty = ancTotalQty;
	}
	public int getAncCID() {
		return ancCID;
	}
	public void setAncCID(int ancCID) {
		this.ancCID = ancCID;
	}
	public String getAncRegTime() {
		return ancRegTime;
	}
	public void setAncRegTime(String ancRegTime) {
		this.ancRegTime = ancRegTime;
	}
	public int getAncAvgRating() {
		return ancAvgRating;
	}
	public void setAncAvgRating(int ancAvgRating) {
		this.ancAvgRating = ancAvgRating;
	}
	@Override
	public String toString() {
		return "ProductDTO [PID=" + PID + ", pName=" + pName + ", pDetail=" + pDetail + ", costPrice=" + costPrice
				+ ", regularPrice=" + regularPrice + ", sellingPrice=" + sellingPrice + ", pQty=" + pQty
				+ ", ingredient=" + ingredient + ", usage=" + usage + ", exp=" + exp + ", category=" + category
				+ ", regTime=" + regTime + ", sellingState=" + sellingState + ", imagePath=" + imagePath
				+ ", searchCondition=" + searchCondition + ", ancSelectMin=" + ancSelectMin + ", ancSelectMax="
				+ ancSelectMax + ", ancTotalQty=" + ancTotalQty + ", ancCID=" + ancCID + ", ancRegTime=" + ancRegTime
				+ ", ancAvgRating=" + ancAvgRating + "]";
	}
    
}
