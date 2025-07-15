package kr.ac.kopo.service;

import kr.ac.kopo.dao.CartDAO;
import kr.ac.kopo.vo.CartVO;
import java.util.List;

public class CartService {

	private CartDAO dao = new CartDAO();

	/**
	 * 회원의 장바구니 전체 조회
	 * 
	 * @param userId 회원 아이디
	 * @return 장바구니 항목 리스트
	 */
	public List<CartVO> getCartByUser(String userId) {
		return dao.selectCartByUser(userId);
	}

	/**
	 * 장바구니 상품 추가
	 * 
	 * @param cart 장바구니 항목 객체
	 */
	public void addCartItem(CartVO cart) {
		dao.insert(cart);
	}

	/**
	 * 장바구니 단일 항목 삭제
	 * 
	 * @param cartId 삭제할 장바구니 항목 ID
	 */
	public void removeCartItem(int cartId) {
		dao.delete(cartId);
	}

	/**
	 * 장바구니 전체 비우기
	 * 
	 * @param userId 회원 아이디
	 */
	public void clearCart(String userId) {
		dao.clearCart(userId);
	}
}
