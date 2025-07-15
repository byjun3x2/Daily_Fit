package kr.ac.kopo.ui;

import kr.ac.kopo.dao.UserDAO;

public class FindPwUI extends BaseUI {
	@Override
	public void execute() throws Exception {
		String id = scanStr("아이디 : ");
		String email = scanStr("이메일 : ");

		// DAO에서 아이디와 이메일로 비밀번호 조회
		String password = new UserDAO().findPassword(id, email);

		if (password != null) {
			System.out.println("\n\t비밀번호: " + maskPassword(password)); // 마스킹 적용
		} else {
			System.out.println("\n\t일치하는 정보가 없습니다");
		}
	}

	// 비밀번호 마스킹 메서드 추가
	private String maskPassword(String password) {
		if (password == null || password.isEmpty()) {
			return "****";
		}

		int length = password.length();
		if (length <= 2) {
			return "****"; // 2글자 이하일 경우 전체 마스킹
		}

		StringBuilder masked = new StringBuilder();
		masked.append(password.charAt(0)); // 첫 번째 문자
		for (int i = 1; i < length - 1; i++) {
			masked.append("*"); // 중간 문자 마스킹
		}
		masked.append(password.charAt(length - 1)); // 마지막 문자
		return masked.toString();
	}
}
