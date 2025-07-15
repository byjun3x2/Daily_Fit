package kr.ac.kopo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.ReviewVO;

public class ReviewDAO {

	// 리뷰 등록
	public void insert(ReviewVO review) {
		String sql = "INSERT INTO reviews(review_id, product_id, user_id, content, rating, created_date) "
				+ "VALUES(reviews_seq.nextval, ?, ?, ?, ?, SYSDATE)";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, review.getProductId());
			pstmt.setString(2, review.getUserId());
			pstmt.setString(3, review.getContent());
			pstmt.setInt(4, review.getRating());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품별 리뷰 목록 조회 (최신순)
	public List<ReviewVO> selectByProduct(int productId) {
		List<ReviewVO> list = new ArrayList<>();
		String sql = "SELECT * FROM reviews WHERE product_id = ? ORDER BY created_date DESC";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReviewVO review = new ReviewVO();
					review.setReviewId(rs.getInt("review_id"));
					review.setProductId(rs.getInt("product_id"));
					review.setUserId(rs.getString("user_id"));
					review.setContent(rs.getString("content"));
					review.setRating(rs.getInt("rating"));
					review.setCreatedDate(rs.getDate("created_date"));
					list.add(review);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 해당 사용자가 해당 상품에 이미 리뷰를 작성했는지 확인 (중복 리뷰 방지)
	public boolean existsUserReview(String userId, int productId) {
		String sql = "SELECT 1 FROM reviews WHERE user_id = ? AND product_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setInt(2, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next(); // 이미 리뷰가 있으면 true
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
