package game;

import javax.swing.JFrame;

public class Main {
	
//	画面の横幅
	private static final int WIDTH = 256+16;
//	画面の高さ
	private static final int HEIGHT = 256+38;
		
		
//	グリッドクラス
	private static GridInfo Ginfo;
		
	
//	呼び出し元
	public static void main(String[] args) {
		
		GameFrame gameFrame = new GameFrame();
		
		gameFrame.setTitle("15Puzzles");
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameFrame.setSize(WIDTH,HEIGHT);
		
		gameFrame.setVisible(true);
		
	}
}


