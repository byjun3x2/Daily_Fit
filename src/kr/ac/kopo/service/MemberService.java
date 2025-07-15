package kr.ac.kopo.service;

import kr.ac.kopo.dao.MemberDAO;
import kr.ac.kopo.vo.UserVO;
import java.util.List;

public class MemberService {
	private MemberDAO dao = new MemberDAO();

	public List<UserVO> getAllMembers() {
		return dao.selectAll();
	}
}
