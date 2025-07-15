package kr.ac.kopo.ui;

import kr.ac.kopo.service.ProductService;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.ProductVO;

public class ProductAddUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// 관리자 권한 체크
		if (!LoginSession.isAdmin()) {
			System.out.println("\n\t관리자만 상품을 등록할 수 있습니다.");
			return;
		}

		System.out.println("\n\t--- 상품 등록 ---");
		String name = scanStr("상품명: ");
		String category = scanStr("카테고리: ");
		int price = scanInt("가격: ");
		int stock = scanInt("재고: ");
		String productSize = scanStr("사이즈: ");
		String color = scanStr("색상: ");
		String description = scanStr("상품 상세정보: "); // 상세정보 입력 추가

		ProductVO product = new ProductVO();
		product.setName(name);
		product.setCategory(category);
		product.setPrice(price);
		product.setStock(stock);
		product.setProductSize(productSize);
		product.setColor(color);
		product.setDescription(description); // 상세정보 저장

		new ProductService().addProduct(product);

		System.out.println("\n\t상품이 성공적으로 등록되었습니다.");
	}
}
