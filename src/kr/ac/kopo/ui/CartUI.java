package kr.ac.kopo.ui;

import kr.ac.kopo.service.CartService;
import kr.ac.kopo.service.ProductService;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.CartVO;
import kr.ac.kopo.vo.ProductVO;
import java.util.List;

public class CartUI extends BaseUI {

	private CartService cartService = new CartService();
	private ProductService productService = new ProductService();

	@Override
	public void execute() {
		String userId = LoginSession.getCurrentUser().getId();

		while (true) {
			System.out.println("===================================");
			System.out.println("---------- 장바구니 관리 ----------");
			System.out.println("===================================");
			List<CartVO> cartList = cartService.getCartByUser(userId);

			if (cartList.isEmpty()) {
				System.out.println("장바구니가 비어 있습니다.");
			} else {
				System.out.printf("| %-6s | %-18s | %-8s | %10s | %6s | %-8s | %-10s | %10s |\n", "번호", "상품명", "카테고리",
						"가격", "수량", "사이즈", "색상", "합계");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------");
				for (CartVO cart : cartList) {
					ProductVO product = productService.getProductById(cart.getProductId());
					int total = product.getPrice() * cart.getQuantity();
					System.out.printf("| %-6d | %-18s | %-8s | %,10d | %6d | %-8s | %-10s | %,10d |\n",
							cart.getCartId(), product.getName(), product.getCategory(), product.getPrice(),
							cart.getQuantity(), product.getProductSize(), product.getColor(), total);
				}
				System.out.println(
						"--------------------------------------------------------------------------------------------------------");
			}

			System.out.println("0. 이전 메뉴");
			System.out.println("1. 상품 추가");
			System.out.println("2. 상품 삭제");
			System.out.println("3. 전체 비우기");
			System.out.println("4. 전체구매");
			String choice = scanStr("선택 : ");

			switch (choice) {
			case "1":
				addToCart(userId);
				break;
			case "2":
				if (!cartList.isEmpty()) {
					int cartId = scanInt("삭제할 상품 번호 : ");
					cartService.removeCartItem(cartId);
				}
				break;
			case "3":
				cartService.clearCart(userId);
				System.out.println("장바구니를 비웠습니다.");
				break;
			case "4":
				if (!cartList.isEmpty()) {
					buyAll(cartList, userId);
				} else {
					System.out.println("장바구니가 비어 있습니다.");
				}
				break;
			case "0":
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	private void addToCart(String userId) {
		int productId = scanInt("상품 번호 : ");
		int quantity = scanInt("수량 : ");

		ProductVO product = productService.getProductById(productId);
		if (product == null) {
			System.out.println("존재하지 않는 상품입니다.");
			return;
		}

		CartVO cart = new CartVO();
		cart.setUserId(userId);
		cart.setProductId(productId);
		cart.setQuantity(quantity);

		try {
			cartService.addCartItem(cart);
			System.out.printf("[%s] 상품을 장바구니에 추가했습니다.\n", product.getName());
		} catch (Exception e) {
			System.out.println("장바구니 추가 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	/**
	 * 장바구니 전체구매 기능
	 */
	private void buyAll(List<CartVO> cartList, String userId) {
		int totalPrice = 0;
		boolean allAvailable = true;

		// 1. 재고 체크
		for (CartVO cart : cartList) {
			ProductVO product = productService.getProductById(cart.getProductId());
			if (product.getStock() < cart.getQuantity()) {
				System.out.printf("상품 [%s]의 재고가 부족합니다. (주문수량: %d, 재고: %d)\n", product.getName(), cart.getQuantity(),
						product.getStock());
				allAvailable = false;
			}
		}

		if (!allAvailable) {
			System.out.println("재고가 부족한 상품이 있어 전체구매를 진행할 수 없습니다.");
			return;
		}

		// 2. 구매 처리(재고 차감)
		for (CartVO cart : cartList) {
			ProductVO product = productService.getProductById(cart.getProductId());
			productService.reduceStock(product.getProductId(), cart.getQuantity());
			int itemTotal = product.getPrice() * cart.getQuantity();
			totalPrice += itemTotal;
		}

		// 3. 장바구니 비우기
		cartService.clearCart(userId);

		System.out.printf("전체 상품을 구매하였습니다. 총 결제금액: %,d원\n", totalPrice);
	}
}
