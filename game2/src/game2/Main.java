package game2;

import javax.swing.JFrame;

public class Main {
//	幅
	private static final int WIDTH = 320 + 16;
//	高さ
	private static final int HEIGHT = 340 + 20;
//	枠線横
	private static final int GRID_X = 8;
//	枠線縦
	private static final int GRID_Y = 8;
//	枠の長さ
	private static final int GRID_SIZE = 40;
	
	
//	スタート処理
	public static void main(String[] args) {	
		
//		フレーム精製
		JFrame gameFrame = new JFrame();
		
//		イベントを搭載
		gameFrame.getContentPane().add(new CanvasBoard(WIDTH,HEIGHT,GRID_X,GRID_Y,GRID_SIZE));
		
//		型を形成
		gameFrame.setTitle("オセロ");
		gameFrame.setSize(WIDTH,HEIGHT);
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
	}
}
	



