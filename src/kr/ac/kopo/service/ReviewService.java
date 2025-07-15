package kr.ac.kopo.service;

import kr.ac.kopo.dao.ReviewDAO;
import kr.ac.kopo.vo.ReviewVO;
import java.util.List;

public class ReviewService {

	private ReviewDAO dao = new ReviewDAO();

	/**
	 * 리뷰 등록
	 */
	public void addReview(ReviewVO review) {
		dao.insert(review);
	}

	/**
	 * 상품별 리뷰 목록 조회 (최신순)
	 */
	public List<ReviewVO> getReviewsByProduct(int productId) {
		return dao.selectByProduct(productId);
	}

	/**
	 * 해당 사용자가 해당 상품에 이미 리뷰를 작성했는지 확인
	 */
	public boolean hasUserReviewedProduct(String userId, int productId) {
		return dao.existsUserReview(userId, productId);
	}
}
