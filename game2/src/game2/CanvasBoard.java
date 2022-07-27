package game2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

class CanvasBoard extends JPanel implements MouseListener{
//	幅
    private int WIDTH;
//	高さ
    private int HEIGHT;
//    グリッド横位置
    private int GRID_X;
//    グリッド縦位置
    private int GRID_Y;
//    グリッドサイズ
    private int GRID_SIZE;
//    グリッドクラス
    private GridInfo gridInfo;
    
    
    private String strDisp;
    private ComAI cAI;
    private final int COMYOMICNT = 3;        //コンピュータロジック再帰回数
    private int tesuCnt;    //手数
    
    /* コンストラクタ */
    CanvasBoard(int width, int height, int x, int y, int size){
        WIDTH = width;
        HEIGHT = height;
        GRID_X = x;
        GRID_Y = y;
        GRID_SIZE = size;
        strDisp = "";
        tesuCnt = 0;
        gridInfo = new GridInfo(GRID_X, GRID_Y);
        cAI = new ComAI(GRID_X, GRID_Y);
        this.addMouseListener(this);
    }
//    クリックされたら
    public void mouseClicked(MouseEvent e){
//    	座標の変数
        int clickPosX = 0;
        int clickPosY = 0;
        int clickTileX = 0;
        int clickTileY = 0;
        boolean blnRet;
        int ret;
        
        /* クリックされた座標を取得 */
        clickPosX = e.getX();
        clickPosY = e.getY();

//        表示メッセージ
        strDisp = "";
        
        /* クリック座標からクリックされた石を置く横マスを取得 */
        for(int loop = 0; loop < GRID_X; loop++){
            if(loop * GRID_SIZE <= clickPosX && clickPosX < (loop + 1) * GRID_SIZE){
                clickTileX = loop;
                break;
            }
        }
        /* クリック座標からクリックされた石を置く縦マスを取得 */
        for(int loop = 0; loop < GRID_Y; loop++){
            if(loop * GRID_SIZE <= clickPosY && clickPosY < (loop + 1) * GRID_SIZE){
                clickTileY = loop;
                break;
            }
        }
        
        /* クリックされたマスに石をセットし、囲まれた石を反転する */
        ret = gridInfo.reverseGridStateFlg(clickTileX, clickTileY, false);
        
        if(ret > 0){
            tesuCnt += 1;
            /* 次の手番で置けるマスがあるかどうかを判定する */
            blnRet = gridInfo.chkPath();
            if(blnRet == true){
                strDisp = "置けるマスがないためパスします。";
            
                /* 手番を更新 */
                gridInfo.updTebanFlg();
                
                /* 終了判定 */
                blnRet = gridInfo.chkPath();
                if(blnRet == true){
                    /* 黒、白どちらもパスとなったらゲーム終了 */
                    strDisp = "ゲーム終了！";
                }else{
                    repaint();
                    return;
                }
            }
            /* コンピュータ手番 */
            cAI.setGridInfo(gridInfo, COMYOMICNT);
            ret = cAI.nextComp(gridInfo.getAllGridStone(), COMYOMICNT, tesuCnt, 0, 0, gridInfo.getTebanColor(), gridInfo.getTebanColor(), 0);
            tesuCnt += 1;
            /* 次の手番で置けるマスがあるかどうかを判定する */
            blnRet = gridInfo.chkPath();
            if(blnRet == true){
                /* 手番を更新 */
                gridInfo.updTebanFlg();
                
                /* 終了判定 */
                blnRet = gridInfo.chkPath();
                if(blnRet == true){
                    /* 黒、白どちらもパスとなったらゲーム終了 */
                    strDisp = "ゲーム終了！";
                }else{
                    /* もう一度コンピュータ手番 */
                    cAI.setGridInfo(gridInfo, COMYOMICNT);
                    ret = cAI.nextComp(gridInfo.getAllGridStone(), COMYOMICNT, tesuCnt, 0, 0, gridInfo.getTebanColor(), gridInfo.getTebanColor(), 0);
                }
            }
        }
        repaint();

    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    
//    パネルを形成する
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0,120,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.black);
        
        for(int loopY = 0; loopY < GRID_Y; loopY++){
            g.drawLine(0, loopY * GRID_SIZE, WIDTH, loopY * GRID_SIZE);
        }
        for(int loopX = 0; loopX < GRID_X; loopX++){
            g.drawLine(loopX * GRID_SIZE, 0, loopX * GRID_SIZE, HEIGHT);
        }
        for(int loopY = 0; loopY < GRID_Y; loopY++){
            for(int loopX = 0; loopX < GRID_X; loopX++){
                g.setColor(gridInfo.getGridStoneColor(loopX, loopY));
                g.fillOval(loopX * GRID_SIZE + 5, loopY * GRID_SIZE + 5, 30, 30);
            }
        }
        g.setColor(Color.red);
        g.drawString(strDisp, 60, 180);
    }

	}