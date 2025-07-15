package kr.ac.kopo.util;

import kr.ac.kopo.vo.UserVO;

public class LoginSession {
    private static UserVO currentUser;

    public static void login(UserVO user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static UserVO getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }
}
