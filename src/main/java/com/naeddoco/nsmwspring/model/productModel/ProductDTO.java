package com.naeddoco.nsmwspring.model.productModel;

public class ProductDTO {
	
	private int productID;
	private String productName;
	private String productDetail;
	private int costPrice;
	private int retailPrice;
	private int salePrice;
	private int stock;
	private String ingredient;
	private String usage;
	private String expirationDate;
	private String registerDate;
	private String modifyDate;
	private String saleState;
	
	
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
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
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getSaleState() {
		return saleState;
	}
	public void setSaleState(String saleState) {
		this.saleState = saleState;
	}
	@Override
	public String toString() {
		return "ProductDTO [productID=" + productID + ", productName=" + productName + ", productDetail="
				+ productDetail + ", costPrice=" + costPrice + ", retailPrice=" + retailPrice + ", salePrice="
				+ salePrice + ", stock=" + stock + ", ingredient=" + ingredient + ", usage=" + usage
				+ ", expirationDate=" + expirationDate + ", registerDate=" + registerDate + ", modifyDate=" + modifyDate
				+ ", saleState=" + saleState + "]";
	}
	
}
