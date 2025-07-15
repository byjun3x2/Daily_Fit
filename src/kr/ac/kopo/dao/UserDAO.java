package kr.ac.kopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.UserVO;

public class UserDAO {

	/**
	 * 회원 가입
	 */
	public boolean insert(UserVO user) {
		String sql = "INSERT INTO users(id, password, name, email, phone, role) VALUES(?, ?, ?, ?, ?, ?)";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getPhone());
			pstmt.setString(6, user.getRole() == null ? "USER" : user.getRole()); // 기본값 USER

			int result = pstmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 로그인 처리
	 */
	public UserVO login(String id, String password) {
		String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, id);
			pstmt.setString(2, password);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					UserVO user = new UserVO();
					user.setId(rs.getString("id"));
					user.setPassword(rs.getString("password"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPhone(rs.getString("phone"));
					user.setRole(rs.getString("role")); // 관리자/회원 구분
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 회원 탈퇴
	 */
	public boolean delete(String id) {
		String sql = "DELETE FROM users WHERE id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, id);
			int result = pstmt.executeUpdate();
			return result == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 아이디 중복 체크
	 */
	public boolean isExist(String id) {
		String sql = "SELECT id FROM users WHERE id = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 아이디 찾기 (이름 + 이메일)
	 */
	public String findUserId(String name, String email) {
		String sql = "SELECT id FROM users WHERE name = ? AND email = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.setString(2, email);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 비밀번호 찾기 (아이디 + 이메일)
	 */
	public String findPassword(String id, String email) {
		String sql = "SELECT password FROM users WHERE id = ? AND email = ?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, id);
			pstmt.setString(2, email);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("password");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 비밀번호 변경
	 */
	public boolean updatePassword(String id, String newPassword) {
		String sql = "UPDATE users SET password=? WHERE id=?";
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newPassword);
			pstmt.setString(2, id);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
