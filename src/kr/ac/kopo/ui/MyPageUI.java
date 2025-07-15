package kr.ac.kopo.ui;

import kr.ac.kopo.service.OrderService;
import kr.ac.kopo.service.ReviewService;
import kr.ac.kopo.service.ProductService;
import kr.ac.kopo.service.UserService;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.OrderVO;
import kr.ac.kopo.vo.OrderDetailVO;
import kr.ac.kopo.vo.ReviewVO;
import kr.ac.kopo.vo.ProductVO;
import java.util.List;

public class MyPageUI extends BaseUI {

    private OrderService orderService = new OrderService();
    private ReviewService reviewService = new ReviewService();
    private ProductService productService = new ProductService();
    private UserService userService = new UserService();

    @Override
    public void execute() {
        String userId = LoginSession.getCurrentUser().getId();

        while (true) {
            System.out.println("\n====== 마이페이지 ======");
            System.out.println("1. 구매 내역 조회 및 리뷰 작성");
            System.out.println("2. 비밀번호 변경");
            System.out.println("3. 회원탈퇴");
            System.out.println("0. 이전메뉴");
            int choice = scanInt("선택 : ");

            switch (choice) {
                case 1:
                    viewPurchaseHistoryAndWriteReview(userId);
                    break;
                case 2:
                    changePassword(userId);
                    break;
                case 3:
                    withdraw(userId);
                    return; // 탈퇴 후 마이페이지 나가기
                case 0:
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private void viewPurchaseHistoryAndWriteReview(String userId) {
        List<OrderVO> orders = orderService.getOrdersByUser(userId);
        if (orders.isEmpty()) {
            System.out.println("\n구매 내역이 없습니다.");
            return;
        }

        System.out.println("\n[구매 내역]");
        for (OrderVO order : orders) {
            List<OrderDetailVO> details = orderService.getOrderDetails(order.getOrderId());
            System.out.printf("주문번호: %d | 총금액: %,d원 | 주문일: %tF\n",
                    order.getOrderId(), order.getTotalPrice(), order.getOrderDate());
            for (OrderDetailVO detail : details) {
                ProductVO product = productService.getProductById(detail.getProductId());
                String productName = (product != null) ? product.getName() : "Unknown";
                System.out.printf("  - 상품ID: %d | 상품명: %s | 수량: %d\n",
                        detail.getProductId(), productName, detail.getQuantity());
            }
            System.out.println("-----------------------------------");
        }

        int productId = scanInt("\n리뷰를 작성할 상품ID (0: 뒤로가기): ");
        if (productId == 0) return;

        if (orderService.hasPurchasedProduct(userId, productId)) {
            if (reviewService.hasUserReviewedProduct(userId, productId)) {
                System.out.println("이미 해당 상품에 대한 리뷰를 작성하셨습니다.");
            } else {
                writeReview(productId);
            }
        } else {
            System.out.println("구매한 상품이 아닙니다.");
        }
    }

    private void writeReview(int productId) {
        String content = scanStr("리뷰 내용: ");
        int rating = scanInt("평점 (1~5): ");
        if (rating < 1 || rating > 5) {
            System.out.println("평점은 1~5 사이로 입력하세요.");
            return;
        }

        ReviewVO review = new ReviewVO();
        review.setProductId(productId);
        review.setUserId(LoginSession.getCurrentUser().getId());
        review.setContent(content);
        review.setRating(rating);

        reviewService.addReview(review);
        System.out.println("\n리뷰가 등록되었습니다.");
    }

    // 비밀번호 변경 예시 (구현 필요)
    private void changePassword(String userId) {
        String oldPwd = scanStr("기존 비밀번호: ");
        String newPwd = scanStr("새 비밀번호: ");
        String newPwd2 = scanStr("새 비밀번호 확인: ");
        if (!newPwd.equals(newPwd2)) {
            System.out.println("새 비밀번호가 일치하지 않습니다.");
            return;
        }
        // 실제 비밀번호 변경 로직 필요 (예: userService.updatePassword(userId, newPwd))
        System.out.println("비밀번호 변경 기능은 구현 필요");
    }

    // 회원탈퇴 기능 (자식 데이터 삭제/ON DELETE CASCADE 필요)
    private void withdraw(String userId) {
        String confirm = scanStr("정말로 탈퇴하시겠습니까? (Y/N): ");
        if ("Y".equalsIgnoreCase(confirm)) {
            boolean result = userService.deleteUser(userId);
            if (result) {
                System.out.println("회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
                LoginSession.logout(); // 세션 로그아웃 처리
            } else {
                System.out.println("회원 탈퇴에 실패했습니다. (관련 데이터가 남아있을 수 있습니다)");
            }
        } else {
            System.out.println("탈퇴가 취소되었습니다.");
        }
    }
}
