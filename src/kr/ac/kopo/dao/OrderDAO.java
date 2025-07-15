package kr.ac.kopo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.OrderVO;
import kr.ac.kopo.vo.OrderDetailVO;

public class OrderDAO {

	// 주문 등록 (orders 테이블에 한 건 등록, 주문번호 반환)
	public int insertOrder(OrderVO order) {
		String sql = "INSERT INTO orders(order_id, user_id, order_date, total_price) VALUES(orders_seq.nextval, ?, SYSDATE, ?)";
		String[] generatedColumns = { "order_id" };
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, generatedColumns)) {
			pstmt.setString(1, order.getUserId());
			pstmt.setInt(2, order.getTotalPrice());
			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1); // 생성된 주문번호 반환
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// 주문 상세 등록 (order_details 테이블에 한 건 등록)
	public void insertOrderDetail(OrderDetailVO detail) {
		String sql = "INSERT INTO order_details(detail_id, order_id, product_id, quantity) VALUES(order_details_seq.nextval, ?, ?, ?)";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, detail.getOrderId());
			pstmt.setInt(2, detail.getProductId());
			pstmt.setInt(3, detail.getQuantity());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 구매 이력 확인: 해당 user가 해당 product를 구매한 적이 있는가?
	public boolean existsPurchase(String userId, int productId) {
		String sql = "SELECT 1 FROM orders o JOIN order_details d ON o.order_id = d.order_id "
				+ "WHERE o.user_id = ? AND d.product_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setInt(2, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next(); // 결과가 있으면 true
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원별 주문 내역 조회 (user_id로 필터링)
	public List<OrderVO> selectOrdersByUser(String userId) {
		List<OrderVO> list = new ArrayList<>();
		String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					OrderVO order = new OrderVO();
					order.setOrderId(rs.getInt("order_id"));
					order.setUserId(rs.getString("user_id"));
					order.setOrderDate(rs.getDate("order_date"));
					order.setTotalPrice(rs.getInt("total_price"));
					list.add(order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 주문 상세 내역 조회 (주문번호로)
	public List<OrderDetailVO> selectOrderDetails(int orderId) {
		List<OrderDetailVO> list = new ArrayList<>();
		String sql = "SELECT * FROM order_details WHERE order_id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, orderId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					OrderDetailVO detail = new OrderDetailVO();
					detail.setDetailId(rs.getInt("detail_id"));
					detail.setOrderId(rs.getInt("order_id"));
					detail.setProductId(rs.getInt("product_id"));
					detail.setQuantity(rs.getInt("quantity"));
					list.add(detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
