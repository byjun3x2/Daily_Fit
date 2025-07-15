package kr.ac.kopo.ui;

import kr.ac.kopo.service.UserService;
import kr.ac.kopo.util.LoginSession;

public class AdminDeleteUserUI extends BaseUI {

    @Override
    public void execute() throws Exception {
    	System.out.println("===================================");
        System.out.println("------------ 회원 삭제 ------------");
        System.out.println("===================================");
        String userId = scanStr("삭제할 회원 아이디: ");

        UserService userService = new UserService();
        boolean result = userService.deleteUser(userId);

        if (result) {
			System.out.println("\n\t[ " + userId + " ] 회원이 삭제되었습니다.");
        } else {
            System.out.println("\n\t회원 삭제에 실패했습니다.");
        }
    }
}
