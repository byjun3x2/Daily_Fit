package kr.ac.kopo.vo;

import java.util.Date;

public class ReviewVO {
	private int reviewId;
	private int productId;
	private String userId;
	private String content;
	private int rating;
	private Date createdDate;

	// 기본 생성자, 모든 필드 생성자, getter/setter
	public ReviewVO() {
	}

	public ReviewVO(int reviewId, int productId, String userId, String content, int rating, Date createdDate) {
		this.reviewId = reviewId;
		this.productId = productId;
		this.userId = userId;
		this.content = content;
		this.rating = rating;
		this.createdDate = createdDate;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "ReviewVO [reviewId=" + reviewId + ", productId=" + productId + ", userId=" + userId + ", content="
				+ content + ", rating=" + rating + ", createdDate=" + createdDate + "]";
	}

}
