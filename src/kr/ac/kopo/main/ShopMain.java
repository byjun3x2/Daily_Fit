package kr.ac.kopo.main;

import kr.ac.kopo.ui.MallUI;

public class ShopMain {
	public static void main(String[] args) {
		try {
			new MallUI().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}