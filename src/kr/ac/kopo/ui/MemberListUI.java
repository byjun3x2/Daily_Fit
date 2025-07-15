package kr.ac.kopo.ui;

import kr.ac.kopo.service.MemberService;
import kr.ac.kopo.vo.UserVO;
import java.util.List;

public class MemberListUI extends BaseUI {

	private MemberService memberService = new MemberService();

	@Override
	public void execute() {
		System.out.println("\n=== 전체 회원 목록 ===");
		List<UserVO> members = memberService.getAllMembers();
		System.out.printf("%-15s %-20s %-20s\n", "아이디", "이름", "이메일");
		System.out.println("--------------------------------------------------------");
		for (UserVO user : members) {
			System.out.printf("%-15s %-20s %-20s\n", user.getId(), user.getName(), user.getEmail());
		}
	}
}
