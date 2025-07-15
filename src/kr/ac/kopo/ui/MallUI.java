package kr.ac.kopo.ui;

import kr.ac.kopo.util.LoginSession;

public class MallUI extends BaseUI {

    private String menu() {

System.out.println("===================================");
        if (LoginSession.getCurrentUser() == null) {
            // 비로그인 상태 메뉴
        	System.out.println("\r\n"
        	+ "    ██████╗  █████╗ ██╗██╗     ██╗   ██╗    ███████╗██╗████████╗    \r\n"
        	+ "    ██╔══██╗██╔══██╗██║██║     ╚██╗ ██╔╝    ██╔════╝██║╚══██╔══╝    \r\n"
        	+ "    ██║  ██║███████║██║██║      ╚████╔╝     █████╗  ██║   ██║       \r\n"
        	+ "    ██║  ██║██╔══██║██║██║       ╚██╔╝      ██╔══╝  ██║   ██║       \r\n"
        	+ "    ██████╔╝██║  ██║██║███████╗   ██║       ██║     ██║   ██║       \r\n"
        	+ "    ╚═════╝ ╚═╝  ╚═╝╚═╝╚══════╝   ╚═╝       ╚═╝     ╚═╝   ╚═╝       \r\n"
        	+ "                                                                    \r\n"
        	+ "");
            System.out.println("===================================");
            System.out.println("\t✨✨Daily Fit Clothing Mall✨✨");
            System.out.println("===================================");
            System.out.println("\t0. 상품 검색");
            System.out.println("\t1. 회원가입");
            System.out.println("\t2. 로그인");
            System.out.println("\t3. 아이디 찾기");
            System.out.println("\t4. 비밀번호 찾기");
            System.out.println("\t9. 프로그램 종료");
        } else if (LoginSession.isAdmin()) {
            // 관리자 로그인 상태 메뉴
            System.out.println("\t0. 이전페이지");
            System.out.println("\t1. 상품조회");
            System.out.println("\t2. 상품등록");
            System.out.println("\t3. 장바구니");
            System.out.println("\t4. 회원 삭제");
            System.out.println("\t5. 마이페이지");
            System.out.println("\t6. 로그아웃");
        } else {
            // 일반 회원 로그인 상태 메뉴
//            System.out.println("\t0. 이전페이지");
            System.out.println("\t1. 상품검색");
            System.out.println("\t2. 장바구니");
            System.out.println("\t3. 마이페이지");
            System.out.println("\t4. 로그아웃");
        }
        System.out.println("===================================");
        return scanStr("\t선택 : ");
    }

    @Override
    public void execute() throws Exception {
        while (true) {
            String choice = menu();
            IBoardUI ui = null;
            if (LoginSession.getCurrentUser() == null) {
                // 비로그인 처리
                switch (choice) {
                    case "0":
                        ui = new ProductSearchUI();
                        break;
                    case "1":
                        ui = new UserAddUI();
                        break;
                    case "2":
                        ui = new LoginUI();
                        break;
                    case "3":
                        ui = new FindIdUI();
                        break;
                    case "4":
                        ui = new FindPwUI();
                        break;
                    case "9":
                        ui = new ExitUI();
                        break;
                    default:
                        System.out.println("\n\t잘못된 선택입니다.");
                }
            } else if (LoginSession.isAdmin()) {
                // 관리자 처리
                switch (choice) {
                    case "0":
                        return; // 이전페이지
                    case "1":
                        ui = new ProductSearchUI(); // 상품조회
                        break;
                    case "2":
                        ui = new ProductAddUI(); // 상품등록
                        break;
                    case "3":
                        ui = new CartUI(); // 장바구니
                        break;
                    case "4":
                        ui = new AdminDeleteUserUI(); // 회원 삭제
                        break;
                    case "5":
                        ui = new MyPageUI(); // 마이페이지
                        break;
                    case "6":
                        LoginSession.logout();
                        System.out.println("\n\t로그아웃 되었습니다.");
                        return;
                    default:
                        System.out.println("\n\t잘못된 선택입니다.");
                }
            } else {
                // 일반 회원 처리
                switch (choice) {
                    case "0":
                        return;
                    case "1":
                        ui = new ProductSearchUI();
                        break;
                    case "2":
                        ui = new CartUI();
                        break;
                    case "3":
                        ui = new MyPageUI();
                        break;
                    case "4":
                        LoginSession.logout();
                        System.out.println("\n\t로그아웃 되었습니다.");
                        return;
                    default:
                        System.out.println("\n\t잘못된 선택입니다.");
                }
            }
            if (ui != null) ui.execute();
        }
    }
}
