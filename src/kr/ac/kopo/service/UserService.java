package kr.ac.kopo.service;

import kr.ac.kopo.dao.UserDAO;
import kr.ac.kopo.exception.DuplicateUserException;
import kr.ac.kopo.util.LoginSession;
import kr.ac.kopo.vo.UserVO;

public class UserService {

	private UserDAO dao = new UserDAO();

	/**
	 * 회원가입
	 */
	public void register(UserVO user) throws Exception {
		if (dao.isExist(user.getId())) {
			throw new DuplicateUserException("이미 존재하는 아이디입니다");
		}
		dao.insert(user);
	}

	/**
	 * 로그인
	 */
	public UserVO login(String id, String password) {
		return dao.login(id, password);
	}

	/**
	 * (본인) 회원 탈퇴
	 */
	public boolean deleteAccount(String id) {
		return dao.delete(id);
	}

	/**
	 * (관리자) 회원 삭제
	 */
	public boolean deleteUser(String userId) {
		// 관리자 본인 삭제 방지
		if (LoginSession.getCurrentUser().getId().equals(userId)) {
			System.out.println("관리자 자신은 삭제할 수 없습니다.");
			return false;
		}
		return dao.delete(userId);
	}

	/**
	 * 아이디 중복 체크
	 */
	public boolean isExist(String id) {
		return dao.isExist(id);
	}

	/**
	 * 아이디 찾기
	 */
	public String findUserId(String name, String email) {
		return dao.findUserId(name, email);
	}

	/**
	 * 비밀번호 찾기
	 */
	public String findPassword(String id, String email) {
		return dao.findPassword(id, email);
	}

	/**
	 * 비밀번호 변경
	 */
	public boolean changePassword(String id, String newPassword) {
		return dao.updatePassword(id, newPassword);
	}
	
	
}
