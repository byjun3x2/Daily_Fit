package kr.ac.kopo.ui;

import kr.ac.kopo.exception.DuplicateUserException;
import kr.ac.kopo.service.UserService;
import kr.ac.kopo.vo.UserVO;

public class UserAddUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("===================================");
		System.out.println("------------ 회원 가입 ------------");
		System.out.println("===================================");

		String id = scanStr("아이디 : ");
		String password = scanStr("비밀번호 : ");
		String name = scanStr("이름 : ");
		String email = scanStr("이메일 : ");
		String phone = scanStr("전화번호 : ");

		// 일반 사용자는 role을 "USER"로 고정
		UserVO user = new UserVO(id, password, name, email, phone, "USER");

		try {
			new UserService().register(user);
			System.out.println("\n\t회원 가입이 완료되었습니다");
		} catch (DuplicateUserException e) {
			System.out.println("\n\t오류: " + e.getMessage());
		}
	}
}
