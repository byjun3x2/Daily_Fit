package kr.ac.kopo.ui;

import kr.ac.kopo.dao.UserDAO;

public class FindIdUI extends BaseUI {
	@Override
	public void execute() throws Exception {
		String name = scanStr("이름 : ");
		String email = scanStr("이메일 : ");

		// DAO에서 이름과 이메일로 아이디 조회 구현
		String foundId = new UserDAO().findUserId(name, email);

		if (foundId != null) {
			System.out.println("\n\t회원 아이디: " + foundId);
		} else {
			System.out.println("\n\t일치하는 정보가 없습니다");
		}
	}
}
