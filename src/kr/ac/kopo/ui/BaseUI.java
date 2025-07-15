package kr.ac.kopo.ui;

import java.util.Scanner;

public abstract class BaseUI implements IBoardUI {

	private Scanner sc;
//	protected BoardDAO dao;
//	protected BoardService boardService;

	public BaseUI() {
		sc = new Scanner(System.in);
//		dao = new BoardDAOFactory().getInstance();
//		boardService = new BoardServiceFactory().getInstance();
	}

	protected String scanStr(String msg) {
		System.out.print(msg);
		return sc.nextLine();
	}

	protected int scanInt(String msg) {
		System.out.print(msg);
		int no = Integer.parseInt(sc.nextLine());
		return no;
	}
}
