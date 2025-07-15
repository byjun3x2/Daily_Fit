package kr.ac.kopo.vo;

public class ProductVO {
	private int productId; // 상품 고유번호
	private String name; // 상품명
	private String category; // 카테고리
	private int price; // 가격
	private int stock; // 재고
	private String productSize; // 사이즈
	private String color; // 색상
	private String description; // 상품 상세정보
	private int reviewCount; // 리뷰 개수

	// 기본 생성자
	public ProductVO() {
	}

	// 모든 필드를 받는 생성자
	public ProductVO(int productId, String name, String category, int price, int stock, String productSize,
			String color, String description, int reviewCount) {
		this.productId = productId;
		this.name = name;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.productSize = productSize;
		this.color = color;
		this.description = description;
		this.reviewCount = reviewCount;
	}

	// Getter & Setter
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	@Override
	public String toString() {
		return "ProductVO [productId=" + productId + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", stock=" + stock + ", productSize=" + productSize + ", color=" + color + ", description="
				+ description + ", reviewCount=" + reviewCount + "]";
	}
}
