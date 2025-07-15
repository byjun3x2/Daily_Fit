package kr.ac.kopo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.CartVO;

public class CartDAO {

	// 장바구니 추가
	public void insert(CartVO cart) {
		String sql = "INSERT INTO cart (cart_id, user_id, product_id, quantity) VALUES (cart_seq.nextval, ?, ?, ?)";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cart.getUserId());
			pstmt.setInt(2, cart.getProductId());
			pstmt.setInt(3, cart.getQuantity());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원별 장바구니 조회
	public List<CartVO> selectCartByUser(String userId) {
		List<CartVO> list = new ArrayList<>();
		String sql = "SELECT * FROM cart WHERE user_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					CartVO vo = new CartVO();
					vo.setCartId(rs.getInt("cart_id"));
					vo.setUserId(rs.getString("user_id"));
					vo.setProductId(rs.getInt("product_id"));
					vo.setQuantity(rs.getInt("quantity"));
					vo.setAddedDate(rs.getString("added_date"));
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 장바구니 단일 삭제
	public void delete(int cartId) {
		String sql = "DELETE FROM cart WHERE cart_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, cartId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 장바구니 전체 비우기
	public void clearCart(String userId) {
		String sql = "DELETE FROM cart WHERE user_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
