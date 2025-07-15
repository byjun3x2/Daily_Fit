package kr.ac.kopo.ui;

public class ExitUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		System.out.println("\n\n========================================");
		System.out.println("\t프로그램을 종료합니다.");
		System.out.println("========================================");
		System.exit(0);
	}

}
