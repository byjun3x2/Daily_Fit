package kr.ac.kopo.ui;

import kr.ac.kopo.service.ProductService;
import kr.ac.kopo.service.CartService;
import kr.ac.kopo.service.OrderService;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.ProductVO;
import kr.ac.kopo.vo.CartVO;
import kr.ac.kopo.vo.OrderVO;
import kr.ac.kopo.vo.OrderDetailVO;

import java.util.List;

public class ProductSearchUI extends BaseUI {

	private ProductService productService = new ProductService();
	private List<ProductVO> searchResults;

	@Override
	public void execute() {
		while (true) {
			System.out.println("===================================");
			System.out.println("------------ 상품 검색 ------------");
			System.out.println("===================================");
			System.out.println("0. 뒤로가기");
			System.out.println("1. 전체 상품 조회");
			System.out.println("2. 상품명 검색");
			System.out.println("3. 카테고리 검색");
			System.out.println("4. 가격대 검색");
			System.out.println("5. 장바구니");
			System.out.println("6. 리뷰 많은 순 상품 조회");
			int type = scanInt("검색 방법 선택: ");

			if (type == 0) {
				return;
			} else if (type == 5) {
				if (LoginSession.getCurrentUser() != null) {
					new CartUI().execute();
				} else {
					System.out.println("\n로그인 후 이용 가능합니다.");
				}
				continue;
			} else if (type == 6) {
				searchResults = productService.getProductsByReviewCountDesc();
				printSearchResultsWithReviewCount();
				continue;
			}

			switch (type) {
			case 1:
				searchResults = productService.getAllProducts();
				printSearchResults();
				break;
			case 2:
				String name = scanStr("상품명: ");
				searchResults = productService.searchByName(name);
				printSearchResults();
				break;
			case 3:
				String category = scanStr("카테고리: ");
				searchResults = productService.searchByCategory(category);
				printSearchResults();
				break;
			case 4:
				int min = scanInt("최소 가격: ");
				int max = scanInt("최대 가격: ");
				searchResults = productService.searchByPriceRange(min, max);
				printSearchResults();
				break;
			default:
				System.out.println("잘못된 선택입니다.");
				continue;
			}

			if (searchResults == null || searchResults.isEmpty()) {
				System.out.println("\n검색 결과가 없습니다.");
				continue;
			}

			if (LoginSession.getCurrentUser() != null) {
				processPurchase();
			} else {
				System.out.println("\n구매를 원하시면 로그인 해주세요.");
			}
		}
	}

	// 6번 메뉴: 리뷰수 포함 표 출력
	private void printSearchResultsWithReviewCount() {
		if (searchResults == null || searchResults.isEmpty()) {
			System.out.println("\n검색 결과가 없습니다.");
			return;
		}
		System.out.printf("| %-6s | %-18s | %-8s | %15s | %6s | %-8s | %-10s | %8s |\n", "상품ID", "상품명", "카테고리", "가격",
				"재고", "사이즈", "색상", "리뷰수");
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		for (ProductVO vo : searchResults) {
			System.out.printf("| %-6d | %-18s | %-8s | %,15d | %6d | %-8s | %-10s | %8d |\n", vo.getProductId(),
					vo.getName(), vo.getCategory(), vo.getPrice(), vo.getStock(), vo.getProductSize(), vo.getColor(),
					vo.getReviewCount());
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
	}

	// 기존 검색 결과 표(리뷰수 미포함)
	private void printSearchResults() {
		if (searchResults == null || searchResults.isEmpty()) {
			System.out.println("\n검색 결과가 없습니다.");
			return;
		}
		// 상품명 20자, 카테고리 8자 등 각 컬럼 너비를 고정
		String format = "| %-6s | %-20s | %-8s | %15s | %6s | %-8s | %-10s | %8s |\n";
		System.out.printf(format, "상품ID", "상품명", "카테고리", "가격", "재고", "사이즈", "색상", "리뷰수");
		System.out.println(
				"---------------------------------------------------------------------------------------------------");
		for (ProductVO vo : searchResults) {
			System.out.printf("| %-6d | %-20s | %-8s | %,15d | %6d | %-8s | %-10s | %8d |\n", vo.getProductId(),
					vo.getName().length() > 20 ? vo.getName().substring(0, 20) : vo.getName(), // 20자 초과시 자름
					vo.getCategory(), vo.getPrice(), vo.getStock(), vo.getProductSize(), vo.getColor(),
					vo.getReviewCount());
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------");
	}

	// 상품 상세/구매/리뷰/바로구매 진입
	private void processPurchase() {
		while (true) {
			int productId = scanInt("\n상세보기/구매할 상품ID (0: 뒤로가기): ");
			if (productId == 0)
				return;

			ProductVO selectedProduct = findProductById(productId);
			if (selectedProduct == null) {
				System.out.println("잘못된 상품ID입니다.");
				continue;
			}

			printProductDetail(selectedProduct);

			System.out.println("\n1. 장바구니 담기");
			System.out.println("2. 리뷰 목록 보기"); // 리뷰 작성은 제거
			System.out.println("3. 바로구매");
			System.out.println("0. 뒤로가기");
			int choice = scanInt("선택 : ");

			switch (choice) {
			case 1:
				int quantity = scanInt("구매 수량: ");
				if (quantity > selectedProduct.getStock()) {
					System.out.println("재고가 부족합니다.");
					break;
				}
				addToCart(selectedProduct, quantity);
				break;
			case 2:
				try {
					new ReviewUI(productId).execute(); // 리뷰 목록만 조회
				} catch (Exception e) {
					System.out.println("리뷰 페이지 이동 중 오류: " + e.getMessage());
				}
				break;
			case 3:
				int buyQty = scanInt("구매 수량: ");
				if (buyQty > selectedProduct.getStock()) {
					System.out.println("재고가 부족합니다.");
					break;
				}
				buyNow(selectedProduct, buyQty);
				break;
			case 0:
				return;
			default:
				System.out.println("잘못된 선택입니다.");
			}
		}
	}

	// 상품 상세정보 출력
	private void printProductDetail(ProductVO vo) {
		System.out.println("\n========== 상품 상세정보 ==========");
		System.out.println("상품ID      : " + vo.getProductId());
		System.out.println("상품명      : " + vo.getName());
		System.out.println("카테고리    : " + vo.getCategory());
		System.out.printf("가격        : %,d원\n", vo.getPrice());
		System.out.println("재고        : " + vo.getStock());
		System.out.println("사이즈      : " + vo.getProductSize());
		System.out.println("색상        : " + vo.getColor());
		System.out.println("상세정보    : " + vo.getDescription());
		System.out.println("리뷰수      : " + vo.getReviewCount());
		System.out.println("==================================");
	}

	// 검색 결과에서 상품ID로 상품 찾기
	private ProductVO findProductById(int productId) {
		for (ProductVO vo : searchResults) {
			if (vo.getProductId() == productId) {
				return vo;
			}
		}
		return null;
	}

	// 장바구니 담기
	private void addToCart(ProductVO product, int quantity) {
		CartVO cart = new CartVO();
		cart.setUserId(LoginSession.getCurrentUser().getId());
		cart.setProductId(product.getProductId());
		cart.setQuantity(quantity);

		try {
			new CartService().addCartItem(cart);
			System.out.printf("\n[%s] %d개가 장바구니에 추가되었습니다.\n", product.getName(), quantity);
		} catch (Exception e) {
			System.out.println("\n장바구니 추가 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	// 바로구매(주문/주문상세 등록, 재고 차감)
	private void buyNow(ProductVO product, int quantity) {
		String userId = LoginSession.getCurrentUser().getId();
		int totalPrice = product.getPrice() * quantity;

		// 1. 주문 등록
		OrderVO order = new OrderVO();
		order.setUserId(userId);
		order.setTotalPrice(totalPrice);
		int orderId = new OrderService().addOrder(order);

		// 2. 주문상세 등록
		OrderDetailVO detail = new OrderDetailVO();
		detail.setOrderId(orderId);
		detail.setProductId(product.getProductId());
		detail.setQuantity(quantity);
		new OrderService().addOrderDetail(detail);

		// 3. 재고 차감
		new ProductService().reduceStock(product.getProductId(), quantity);

		System.out.printf("구매가 완료되었습니다! 결제금액: %,d원\n", totalPrice);
	}
}
