package kr.ac.kopo.vo;

public class CartVO {
	private int cartId; // 장바구니 항목 고유번호 (PK)
	private String userId; // 회원 아이디 (FK)
	private int productId; // 상품 번호 (FK)
	private int quantity; // 담은 수량
	private String addedDate; // 장바구니에 담은 날짜

	// 기본 생성자
	public CartVO() {
	}

	// 모든 필드를 받는 생성자
	public CartVO(int cartId, String userId, int productId, int quantity, String addedDate) {
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.addedDate = addedDate;
	}

	// Getter & Setter
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "CartVO [cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity
				+ ", addedDate=" + addedDate + "]";
	}
}
