package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame implements MouseListener{
	
//	ゲームの状態フラグ　タイトル
	private static final int GAME_WAIT = 0;
//	ゲームの状態フラグ　ゲーム中
	private static final int GAME_ING = 1;
//	ボードの横マス
	private static final int Gx = 4;
//　ボードの縦マス
	private static final int Gy = 4;
//	マスの横幅
	private static final int Grid_width = 64;
//	マスの縦幅
	private static final int Grid_height = 64;
//	ゲーム状態のフラグ
	private static int gameFlag;
//	グリッドクラス
	private static GridInfo Ginfo;
//	画像配列
	private static ImageIcon titleImage[];
//	ラベル
	private static JLabel label1[];
	
	
//	コンストラクタ
	GameFrame(){
		
//		GInfo呼び出し
		Ginfo = new GridInfo(Gx,Gy);
//		イメージの定義
		titleImage = new ImageIcon[Gx*Gy+1];
//		ラベル定義
		label1 = new JLabel[Gx*Gy+1];
		
		this.getContentPane().setLayout(null);
		
		
//		1~15までのコマ画像を読み込み
		DecimalFormat decimalFormat = new DecimalFormat("00");
		
		for(int i = 1;i < Gx*Gy;i++) {
			titleImage[i] = new ImageIcon("./src/game/images/"+decimalFormat.format(i)+".gif");
			System.out.println(titleImage[i]);
			label1[i] = new JLabel(titleImage[i]);
			
			this.getContentPane().add(label1[i]);
		
		}
			
//		1~15までのコマをボード上に配置
		for(int y = 0;y < Gy; y++) {
			for(int x = 0;x<Gx;x++) {
				if(Ginfo.getTileNum(x,y) !=0) {
					label1[Ginfo.getTileNum(x,y)].setBounds(x*Grid_width,y*Grid_height,Grid_width,Grid_height);
				}
			
			}
		}
			this.getContentPane().addMouseListener(this);
		
		}
		
		
//		ゲーム初期
		public void gemeInit() {
			Ginfo.shfleTile();
			
			gameFlag = GAME_ING;
		}
		@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				
		}
		public void mousePressed(MouseEvent e) {
			int clicktitlex;
			int clicktitley;
			boolean blnRet;
			
			switch(gameFlag) {
			case GAME_WAIT:
				System.out.println("GAME START");
				gemeInit();
				break;
			case GAME_ING:
//				クリックされた位置を取得
				clicktitlex = (int)((e.getX()) / Grid_width);
				clicktitley = (int)((e.getY()) / Grid_height);
				
				blnRet = Ginfo.moveTile(clicktitlex,clicktitley);
				
				blnRet = Ginfo.getGameClearFlag();
				
				if(blnRet == true) {
					gameFlag = GAME_WAIT;
					System.out.println("GAME OVER");
					
				}
				break;
			}
				
				for(int y = 0;y<Gy;y++) {
					for(int x = 0;x<Gx;x++) {
						if(Ginfo.getTileNum(x,y) !=0) {
							label1[Ginfo.getTileNum(x,y)].setBounds(x*Grid_width,y*Grid_height,Grid_width,Grid_height);
						}
					}
				}
				
				this.setVisible(true);
				
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				
			}
			
		}