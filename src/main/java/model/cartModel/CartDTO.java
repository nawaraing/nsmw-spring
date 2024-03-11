package model.cartModel;

public class CartDTO {

	private int cartID;
	private String memberID;
	private int productID;
	private int productQuantity;
	
	public int getCartID() {
		return cartID;
	}
	public void setCartID(int cartID) {
		this.cartID = cartID;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	@Override
	public String toString() {
		return "CartDTO [cartID=" + cartID + ", memberID=" + memberID + ", productID=" + productID
				+ ", productQuantity=" + productQuantity + ", toString()=" + super.toString() + "]";
	}
	
}
