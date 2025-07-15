package kr.ac.kopo.service;

import kr.ac.kopo.dao.OrderDAO;
import kr.ac.kopo.vo.OrderVO;
import kr.ac.kopo.vo.OrderDetailVO;
import java.util.List;

public class OrderService {

	private OrderDAO dao = new OrderDAO();

	/**
	 * 주문 등록 (orders 테이블에 한 건 등록, 주문번호 반환)
	 */
	public int addOrder(OrderVO order) {
		return dao.insertOrder(order);
	}

	/**
	 * 주문 상세 등록 (order_details 테이블에 한 건 등록)
	 */
	public void addOrderDetail(OrderDetailVO detail) {
		dao.insertOrderDetail(detail);
	}

	/**
	 * 구매 이력 확인 (해당 사용자가 해당 상품을 구매했는지)
	 */
	public boolean hasPurchasedProduct(String userId, int productId) {
		return dao.existsPurchase(userId, productId);
	}

	/**
	 * 사용자 주문 내역 조회
	 */
	public List<OrderVO> getOrdersByUser(String userId) {
		return dao.selectOrdersByUser(userId);
	}

	/**
	 * 주문 상세 내역 조회 (주문번호로)
	 */
	public List<OrderDetailVO> getOrderDetails(int orderId) {
		return dao.selectOrderDetails(orderId);
	}
}
