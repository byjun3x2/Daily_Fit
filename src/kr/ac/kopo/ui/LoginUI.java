package kr.ac.kopo.ui;

import kr.ac.kopo.service.UserService;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.UserVO;

public class LoginUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("===================================");
		System.out.println("------------- 로그인 -------------");
		System.out.println("===================================");

		String id = scanStr("아이디 : ");
		String password = scanStr("비밀번호 : ");

		UserVO user = new UserService().login(id, password);
		if (user != null) {
			LoginSession.login(user); // 로그인 세션에 저장
			if ("ADMIN".equalsIgnoreCase(user.getRole())) {
				System.out.printf("\n\t관리자 %s님 환영합니다!\n", user.getName());
			} else {
				System.out.printf("\n\t%s님 환영합니다!\n", user.getName());
			}
		} else {
			System.out.println("\n\t로그인에 실패하였습니다");
		}
	}
}
