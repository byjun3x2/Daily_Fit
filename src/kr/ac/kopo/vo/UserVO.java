package kr.ac.kopo.vo;

public class UserVO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String role; // 관리자/일반회원 구분을 위한 필드

	// 기본 생성자
	public UserVO() {
	}

	// 모든 필드를 받는 생성자
	public UserVO(String id, String password, String name, String email, String phone, String role) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	// Getter & Setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", role=" + role + "]";
	}
}
