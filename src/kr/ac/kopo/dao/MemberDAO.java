package kr.ac.kopo.dao;

import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.UserVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	public List<UserVO> selectAll() {
		List<UserVO> list = new ArrayList<>();
		String sql = "SELECT id, name, email FROM users ORDER BY id";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
