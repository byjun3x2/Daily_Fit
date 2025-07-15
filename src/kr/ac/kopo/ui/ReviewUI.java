package kr.ac.kopo.ui;

import kr.ac.kopo.service.ReviewService;
import kr.ac.kopo.vo.ReviewVO;
import java.util.List;

public class ReviewUI extends BaseUI {

	private int productId;
	private ReviewService reviewService = new ReviewService();

	public ReviewUI(int productId) {
		this.productId = productId;
	}

	@Override
	public void execute() {
		System.out.println("\n--- 리뷰페이지 ---");
		listReviews(); // 리뷰 목록 바로 출력
	}

	private void listReviews() {
		List<ReviewVO> reviews = reviewService.getReviewsByProduct(productId);
		if (reviews.isEmpty()) {
			System.out.println("\n등록된 리뷰가 없습니다.");
			return;
		}

		System.out.println("===========================================");
		for (ReviewVO review : reviews) {
			System.out.printf("작성자: %s | 평점: %d/5 | 작성일: %tF\n", review.getUserId(), review.getRating(),
					review.getCreatedDate());
			System.out.printf("내용: %s\n", review.getContent());
			System.out.println("-------------------------------------------");
		}
	}
}
