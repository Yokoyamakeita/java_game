package game2;

import java.awt.Color;

public class GridInfo {
	
    public final int NON_STATE = 0;
    public final int BLACK_STATE = 1;
    public final int WHITE_STATE = 2;
    private int GRID_X;
    private int GRID_Y;
    private int gridStateFlg[][];
    private int tebanFlg;
    
    /* コンストラクタ */
    GridInfo(int gridXCnt, int gridYCnt){
        GRID_X = gridXCnt;
        GRID_Y = gridYCnt;
        gridStateFlg = new int[GRID_Y][GRID_X];
        for(int loopY = 0; loopY < GRID_Y; loopY++){
            for(int loopX = 0; loopX < GRID_X; loopX++){
                gridStateFlg[loopY][loopX] = NON_STATE;
            }
        }
        /* 石の初期配置をセット */
        gridStateFlg[3][3] = WHITE_STATE;
        gridStateFlg[4][4] = WHITE_STATE;
        gridStateFlg[4][3] = BLACK_STATE;
        gridStateFlg[3][4] = BLACK_STATE;
        /* 先番を黒で設定 */
        tebanFlg = BLACK_STATE;
    }
    /* 指定したマスに置かれる石をセットするメソッド */
    public void setGridStateFlg(int x, int y){
        if(tebanFlg == BLACK_STATE){
            gridStateFlg[y][x] = BLACK_STATE;
            tebanFlg = WHITE_STATE;
        }else if(tebanFlg == WHITE_STATE){
            gridStateFlg[y][x] = WHITE_STATE;
            tebanFlg = BLACK_STATE;
        }else{
            gridStateFlg[y][x] = NON_STATE;
        }
    }
    /* 指定したマスに置かれる石をセットするメソッド（色指定付き） */
    public void setGridStateFlg(int x, int y, int ownColor){
        if(ownColor == BLACK_STATE){
            gridStateFlg[y][x] = BLACK_STATE;
        }else if(ownColor == WHITE_STATE){
            gridStateFlg[y][x] = WHITE_STATE;
        }else{
            gridStateFlg[y][x] = NON_STATE;
        }
    }
    /* 指定したマスに置かれる石を取得するメソッド */
    public int getGridStateFlg(int x, int y){
        return gridStateFlg[y][x];
    }
    /* 指定したマスに置かれている石の色を取得するメソッド */
    public Color getGridStoneColor(int x, int y){
        switch(gridStateFlg[y][x]){
            case BLACK_STATE:
                return Color.black;
            case WHITE_STATE:
                return Color.white;
            default:
                return (new Color(0,120,0));
        }
    }
	    /* 現在の手番の石の色を取得するメソッド */
	    public int getTebanColor(){
	        return tebanFlg;
	    }
	    /* 囲まれた石を反転する（反転対象の抽出） */
	    public int reverseGridStateFlg(int gridX, int gridY, boolean chkOnlyFlg){
	        return reverseGridStateFlg(gridX, gridY, chkOnlyFlg, tebanFlg);
	    }
	    /* 囲まれた石を反転する（反転対象の抽出） */
	    public int reverseGridStateFlg(int gridX, int gridY, boolean chkOnlyFlg, int tebanColor){
	        boolean blnRet;
	        int ownColor;        //手番の石色
	        int revColor;        //反転する石色
	        int revCnt = 0;          //反転した石の総数
	        int leftRevCnt = 0;      //左方向へ反転した石の総数
	        int leftTopRevCnt = 0;   //左上方向へ反転した石の総数
	        int topRevCnt = 0;       //上方向へ反転した石の総数
	        int rightTopRevCnt = 0;  //右上方向へ反転した石の総数
	        int rightRevCnt = 0;     //右方向へ反転した石の総数
	        int rightDownRevCnt = 0; //右下方向へ反転した石の総数
	        int downRevCnt = 0;      //下方向へ反転した石の総数
	        int leftDownRevCnt = 0;  //左下方向へ反転した石の総数
	        int loopX;
	        int loopY;
	        int gridStateTemp;
	        
	        gridStateTemp = this.getGridStateFlg(gridX, gridY);
	        ownColor = tebanColor;
	        
	        /* クリックされたマスに何も置かれていないことを判定 */
	        if(gridStateTemp == NON_STATE){
	            /* 反転色を設定 */
	            switch(ownColor){
	                case BLACK_STATE:
	                    revColor = WHITE_STATE;
	                    break;
	                default:
	                    revColor = BLACK_STATE;
	                    break;
	            }
	            
	            /* 左方向への反転チェック */
	            loopX = gridX - 1;
	            while(this.chkReverse(loopX, gridY, revColor)){
	                leftRevCnt += 1;
	                loopX -= 1;
	                
	                if(loopX < 0){
	                    leftRevCnt = 0;
	                    break;
	                }
	            }
	            if(leftRevCnt > 0){
	                if(gridStateFlg[gridY][loopX] != ownColor){
	                    leftRevCnt = 0;
	                }
	            }
	            
	            /* 左上方向への反転チェック */
	            loopX = gridX - 1;
	            loopY = gridY - 1;
	            while(this.chkReverse(loopX, loopY, revColor)){
	                leftTopRevCnt += 1;
	                loopX -= 1;
	                loopY -= 1;
	                
	                if(loopX < 0 || loopY < 0){
	                    leftTopRevCnt = 0;
	                    break;
	                }
	            }
	            if(leftTopRevCnt > 0){
	                if(gridStateFlg[loopY][loopX] != ownColor){
	                    leftTopRevCnt = 0;
	                }
	            }
	            
	            /* 上方向への反転チェック */
	            loopY = gridY - 1;
	            while(this.chkReverse(gridX, loopY, revColor)){
	                topRevCnt += 1;
	                loopY -= 1;
	                
	                if(loopY < 0){
	                    topRevCnt = 0;
	                    break;
	                }
	            }
	            if(topRevCnt > 0){
	                if(gridStateFlg[loopY][gridX] != ownColor){
	                    topRevCnt = 0;
	                }
	            }
	            
	            /* 右上方向への反転チェック */
	            loopX = gridX + 1;
	            loopY = gridY - 1;
	            while(this.chkReverse(loopX, loopY, revColor)){
	                rightTopRevCnt += 1;
	                loopX += 1;
	                loopY -= 1;
	                
	                if(loopX >= GRID_X || loopY < 0){
	                    rightTopRevCnt = 0;
	                    break;
	                }
	            }
	            if(rightTopRevCnt > 0){
	                if(gridStateFlg[loopY][loopX] != ownColor){
	                    rightTopRevCnt = 0;
	                }
	            }
	            
	            /* 右方向への反転チェック */
	            loopX = gridX + 1;
	            while(this.chkReverse(loopX, gridY, revColor)){
	                rightRevCnt += 1;
	                loopX += 1;
	                
	                if(loopX >= GRID_X){
	                    rightRevCnt = 0;
	                    break;
	                }
	            }
	            if(rightRevCnt > 0){
	                if(gridStateFlg[gridY][loopX] != ownColor){
	                    rightRevCnt = 0;
	                }
	            }
	            
	            /* 右下方向への反転チェック */
	            loopX = gridX + 1;
	            loopY = gridY + 1;
	            while(this.chkReverse(loopX, loopY, revColor)){
	                rightDownRevCnt += 1;
	                loopX += 1;
	                loopY += 1;
	                
	                if(loopX >= GRID_X || loopY >= GRID_Y){
	                    rightDownRevCnt = 0;
	                    break;
	                }
	            }
	            if(rightDownRevCnt > 0){
	                if(gridStateFlg[loopY][loopX] != ownColor){
	                    rightDownRevCnt = 0;
	                }
	            }
	            
	            /* 下方向への反転チェック */
	            loopY = gridY + 1;
	            while(this.chkReverse(gridX, loopY, revColor)){
	                downRevCnt += 1;
	                loopY += 1;
	                
	                if(loopY >= GRID_Y){
	                    downRevCnt = 0;
	                    break;
	                }
	            }
	            if(downRevCnt > 0){
	                if(gridStateFlg[loopY][gridX] != ownColor){
	                    downRevCnt = 0;
	                }
	            }
	            
	            /* 左下方向への反転チェック */
	            loopX = gridX - 1;
	            loopY = gridY + 1;
	            while(this.chkReverse(loopX, loopY, revColor)){
	                leftDownRevCnt += 1;
	                loopX -= 1;
	                loopY += 1;
	                
	                if(loopX < 0 || loopY >= GRID_Y){
	                    leftDownRevCnt = 0;
	                    break;
	                }
	            }
	            if(leftDownRevCnt > 0){
	                if(gridStateFlg[loopY][loopX] != ownColor){
	                    leftDownRevCnt = 0;
	                }
	            }
	            
	            /* 反転する石の総数を算出 */
	            revCnt = leftRevCnt + leftTopRevCnt + topRevCnt + rightTopRevCnt + rightRevCnt + rightDownRevCnt + downRevCnt + leftDownRevCnt;
	            
	            /* 反転対象がなければ */
	            if(revCnt < 1){
	                //何もせずに終了
	            
	            /* 反転対象があれば */
	            }else{
	                /* 実際に石を反転する場合（反転対象有無のチェックのみであれば何もしない） */
	                if(chkOnlyFlg == false){
	                    /* 囲んだ石を反転する */
	                    blnRet = this.actionReverseGrid(gridX, gridY, ownColor, leftRevCnt, leftTopRevCnt, topRevCnt, rightTopRevCnt, rightRevCnt, rightDownRevCnt, downRevCnt, leftDownRevCnt);
	                    /* 今回のターンで置かれた石をセットする */
	                    this.setGridStateFlg(gridX, gridY, ownColor);
	                    /* 手番フラグを更新 */
	                    switch(ownColor){
	                        case BLACK_STATE:
	                            tebanFlg =  WHITE_STATE;
	                            break;
	                        default:
	                            tebanFlg =  BLACK_STATE;
	                            break;
	                    }
	                }
	            }
	        }
	        /* 戻り値には反転した石の総数を指定 */
	        return revCnt;
	    }
	    /* 囲まれた石を反転する */
	    private boolean actionReverseGrid(int gridX, int gridY, int revColor, int leftRevCnt, int leftTopRevCnt, int topRevCnt, int rightTopRevCnt, int rightRevCnt, int rightDownRevCnt, int downRevCnt, int leftDownRevCnt){
	        boolean blnRet;
	        int revLoop;
	        
	        blnRet = false;

	        /* 左方向の反転 */
	        for(revLoop = 1; revLoop <= leftRevCnt; revLoop++){
	            gridStateFlg[gridY][gridX - revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        /* 左上方向の反転 */
	        for(revLoop = 1; revLoop <= leftTopRevCnt; revLoop++){
	            gridStateFlg[gridY - revLoop][gridX - revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        /* 上方向の反転 */
	        for(revLoop = 1; revLoop <= topRevCnt; revLoop++){
	            gridStateFlg[gridY - revLoop][gridX] = revColor;
	            blnRet = true;
	        }
	        
	        /* 右上方向の反転 */
	        for(revLoop = 1; revLoop <= rightTopRevCnt; revLoop++){
	            gridStateFlg[gridY - revLoop][gridX + revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        /* 右方向の反転 */
	        for(revLoop = 1; revLoop <= rightRevCnt; revLoop++){
	            gridStateFlg[gridY][gridX + revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        /* 右下方向の反転 */
	        for(revLoop = 1; revLoop <= rightDownRevCnt; revLoop++){
	            gridStateFlg[gridY + revLoop][gridX + revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        /* 下方向の反転 */
	        for(revLoop = 1; revLoop <= downRevCnt; revLoop++){
	            gridStateFlg[gridY + revLoop][gridX] = revColor;
	            blnRet = true;
	        }
	        
	        /* 左下方向の反転 */
	        for(revLoop = 1; revLoop <= leftDownRevCnt; revLoop++){
	            gridStateFlg[gridY + revLoop][gridX - revLoop] = revColor;
	            blnRet = true;
	        }
	        
	        return blnRet;
	        
	    }
	    /* 反転可能かどうかを判定する */
	    private boolean chkReverse(int gridX, int gridY, int revColor){
	        boolean blnRet;
	        
	        blnRet = false;
	        if((gridX < 0 || gridX >= GRID_X || gridY < 0 || gridY >= GRID_Y) == false){
	            if(gridStateFlg[gridY][gridX] == revColor){
	                blnRet = true;
	            }
	        }
	        
	        return blnRet;
	    }
	    /* パス可能かどうかを判定する */
	    public boolean chkPath(){
	        int revCnt;
	        boolean blnPath;
	        blnPath = true;
	        revCnt = 0;
	        
	        for(int loopY = 0; loopY < GRID_Y; loopY++){
	            for(int loopX = 0; loopX < GRID_X; loopX++){
	                revCnt = this.reverseGridStateFlg(loopX, loopY, true);
	                if(revCnt > 0){
	                    blnPath = false;
	                    break;
	                }
	            }
	        }
	        return blnPath;
	    }
	    /* 手番を更新するメソッド */
	    public void updTebanFlg(){
	        if(tebanFlg == BLACK_STATE){
	            tebanFlg = WHITE_STATE;
	        }else if(tebanFlg == WHITE_STATE){
	            tebanFlg = BLACK_STATE;
	        }else{
	            /* 何もしない */
	        }
	    }
	    public void copyGridState(int gridStateCpMoto[][], int gridStateCpSaki[][]){
	        for(int loopY = 0; loopY < GRID_Y; loopY++){
	            for(int loopX = 0; loopX < GRID_X; loopX++){
	                gridStateCpSaki[loopY][loopX] = gridStateCpMoto[loopY][loopX];
	            }
	        }
	    }
	    public int[][] getAllGridStone(){
	        return gridStateFlg;
	    }
	}
	class ComAI{
	    private final int BUFF_CNT = 1000000;
	    private int preGridStateFlg[][];
	    private GridInfo gridInfo;
	    private int omomiSum[];
	    private int positionCnt;
	    private int positionGridX[];
	    private int positionGridY[];
	    private int comYomiCnt;
	    private int GRID_X;
	    private int GRID_Y;
	    
	    ComAI(int gridX, int gridY){
	        omomiSum = new int[BUFF_CNT];
	        positionCnt = 0;
	        positionGridX = new int[BUFF_CNT];
	        positionGridY = new int[BUFF_CNT];
	        GRID_X = gridX;
	        GRID_Y = gridY;
	        preGridStateFlg = new int[GRID_Y][GRID_X];
	    }
	    public void setGridInfo(GridInfo gInfo, int yomiCnt){
	        gridInfo = gInfo;
	        gridInfo.copyGridState(gInfo.getAllGridStone(), preGridStateFlg);
	        positionCnt = 0;
	        comYomiCnt = yomiCnt;
	    }
	    public int getBeforeGridStone(int gridX, int gridY){
	        return preGridStateFlg[gridY][gridX];
	    }
	    public boolean chkPath(int intComColor){
	        int revCnt;
	        revCnt = 0;
	        for(int loopY = 0; loopY < GRID_Y; loopY++){
	            for(int loopX = 0; loopX < GRID_X; loopX++){
	                revCnt = gridInfo.reverseGridStateFlg(loopX, loopY, false, intComColor);
	                if(revCnt > 0){
	                    return false;
	                }
	            }
	        }
	        return true;
	    }
	    public int getOmomi(int gridX, int gridY, int tesuCnt, int revCnt, int yomiCnt, int comColor, int tebanColor){
	        int omomi;
	        omomi = 0;
	        if(comColor == tebanColor){
	            //四隅
	            if((gridX == 0 && gridY == 0) || (gridX == GRID_X - 1 && gridY == GRID_Y - 1) || (gridX == 0 && gridY == GRID_Y -1 ) || (gridX == GRID_X - 1 && gridY == 0)){
	                omomi = 500 * yomiCnt;
	                
	            }else if(gridX == 0 || gridY == 0 || gridX == GRID_X - 1 || gridY == GRID_Y - 1){
	                omomi = 100 * yomiCnt;
	            }
	            if((gridX == 0 && gridY == 1) || (gridX == 1 && gridY == 0) || (gridX == 1 && gridY == 1) || (gridX == 0 && gridY == GRID_Y -2) || (gridX == 1 && gridY == GRID_Y - 2) || (gridX == 1 && gridY == GRID_Y - 1) || (gridX == GRID_X - 2 && gridY == 0) || (gridX == GRID_X - 2 && gridY == 1) || (gridX == GRID_X -1 && gridY == 1) || (gridX == GRID_X - 2 && gridY == GRID_Y - 1) || (gridX == GRID_X - 2 && gridY == GRID_Y - 2) || (gridX == GRID_X - 1 && gridY == GRID_Y - 2)){
	                omomi = (-1) * 150 * yomiCnt;
	            }
	            omomi = omomi -  (int)((revCnt * 1.5 * ((64 - tesuCnt) * 0.3)) * 0.3);
	        }else{
	            if((gridX == 0 && gridY == 0) || (gridX == GRID_X - 1 && gridY == GRID_Y - 1) || (gridX == 0 && gridY == GRID_Y -1 ) || (gridX == GRID_X - 1 && gridY == 0)){
	                omomi = (-1 * 500 * (yomiCnt + 1)) - 10;
	                
	            }else if(gridX == 0 || gridY == 0 || gridX == GRID_X - 1 || gridY == GRID_Y - 1){
	                omomi = (-1 * 100 * (yomiCnt + 1)) - 10;
	                //System.out.println(yomiCnt + "  " + omomi);
	            }
	            omomi = omomi - revCnt;
	        }
	        return omomi;
	    }
	    public int nextComp(int preGridStateTemp[][], int preYomiCnt, int preTesu, int prePutX, int prePutY, int preComColor, int preTebanColor, int preOmomiSum){
	        int revCnt;
	        int omomi;
	        int finalPutXY[];
	        int ret;
	        int gridArrStateTemp[][];
	        boolean revFlg;
	        
	        revFlg = false;
	        ret = 1;
	        revCnt = 0;
	        omomi = 0;
	        for(int loopY = 0; loopY < GRID_Y; loopY++){
	            for(int loopX = 0; loopX < GRID_X; loopX++){
	                if(gridInfo.getGridStateFlg(loopX, loopY) == 0){ //NON_STATE
	                    gridArrStateTemp = new int[GRID_Y][GRID_X];
	                    gridInfo.copyGridState(preGridStateTemp, gridArrStateTemp);
	                    //System.out.println("preComColor  " + preTebanColor);
	                    revCnt = gridInfo.reverseGridStateFlg(loopX, loopY, false, preTebanColor);
	                    if(revCnt > 0){
	                        omomi = getOmomi(loopX, loopY, preTesu, revCnt, preYomiCnt, preComColor, preTebanColor);
	                        revFlg = true;
	                        if(preYomiCnt == comYomiCnt){
	                            preOmomiSum = 0;
	                            prePutX = loopX;
	                            prePutY = loopY;
	                            omomiSum[positionCnt] = -1 * 10000;
	                        }
	                        if(preYomiCnt > 0){
	                            omomiSum[positionCnt] = preOmomiSum + omomi;
	                            positionGridX[positionCnt] = prePutX;
	                            positionGridY[positionCnt] = prePutY;
	                        }
	                        
	                        if(preYomiCnt <= 0){
	                            omomiSum[positionCnt] = preOmomiSum + omomi;
	                            positionGridX[positionCnt] = prePutX;
	                            positionGridY[positionCnt] = prePutY;
	                            positionCnt += 1;
	                            omomiSum[positionCnt] = -1 * 10000;
	                        }else{
	                            ret = this.nextComp(preGridStateTemp, preYomiCnt - 1, preTesu, prePutX, prePutY, preComColor, (preTebanColor % 2) + 1 , preOmomiSum + omomi);
	                            
	                        }
	                        
	                        gridInfo.copyGridState(gridArrStateTemp, preGridStateTemp);
	                    }
	                    if(preYomiCnt == comYomiCnt && revCnt > 0 && omomiSum[positionCnt] > -1 * 10000){
	                        positionCnt += 1;
	                    }
	                }
	            }
	        }
	        
	        if(preYomiCnt != comYomiCnt && revFlg == false){
	            if(preComColor == preTebanColor){
	                omomiSum[positionCnt] = preOmomiSum - (100 * preYomiCnt);
	            }
	        }
	        if(preYomiCnt == comYomiCnt){
	            //System.out.println("positionCnt " + positionCnt);
	            if(positionCnt > 0){
	                finalPutXY = this.sortOmomi(omomiSum, positionGridX, positionGridY, positionCnt);
	                revCnt = gridInfo.reverseGridStateFlg(finalPutXY[0], finalPutXY[1], false, preComColor);
	                ret = 1;
	            }else{
	                //パス
	                ret = 2;
	            }
	        }
	        return ret;
	    }
	    public int[] sortOmomi(int omomiSum[], int gridX[], int gridY[], int arrCnt){
	        int gridXTemp;
	        int gridYTemp;
	        int maxOmomi;
	        int minOmomi;
	        int allMaxOmomi;
	        int maxOmomiGridX;
	        int maxOmomiGridY;
	        int omomi;
	        int random;
	        int ret[];
	        
	        ret = new int[2];
	        gridXTemp = gridX[0];
	        gridYTemp = gridY[0];
	        allMaxOmomi = -1 * 10000;
	        maxOmomi = -1 * 10000;
	        minOmomi = 10000;
	        maxOmomiGridX = -1;
	        maxOmomiGridY = -1;
	        
	        for(int intLoop = 0; intLoop < arrCnt; intLoop++){
	            if(gridXTemp == gridX[intLoop] && gridYTemp == gridY[intLoop]){
	                if(maxOmomi < omomiSum[intLoop]){
	                    maxOmomi = omomiSum[intLoop];
	                }
	                
	                if(minOmomi > omomiSum[intLoop]){
	                    minOmomi = omomiSum[intLoop];
	                }
	                
	                gridXTemp = gridX[intLoop];
	                gridYTemp = gridY[intLoop];
	                
	            }else{
	                omomi = maxOmomi + minOmomi;
	                
	                if(allMaxOmomi < omomi){
	                    allMaxOmomi = omomi;
	                    maxOmomiGridX = gridXTemp;
	                    maxOmomiGridY = gridYTemp;
	                }else if(allMaxOmomi == omomi){
	                    random = (int)Math.random() * 2;
	                    if(random == 0){
	                        allMaxOmomi = omomi;
	                        maxOmomiGridX = gridXTemp;
	                        maxOmomiGridY = gridYTemp;
	                    }
	                }
	                
	                if(intLoop < arrCnt){
	                    maxOmomi = omomiSum[intLoop];
	                    minOmomi = omomiSum[intLoop];
	                }
	                
	                gridXTemp = gridX[intLoop];
	                gridYTemp = gridY[intLoop];
	                
	            }
	        }
	        
	        omomi = maxOmomi + minOmomi;
	        
	        if(allMaxOmomi < omomi){
	            allMaxOmomi = omomi;
	            maxOmomiGridX = gridXTemp;
	            maxOmomiGridY = gridYTemp;
	        }else if(allMaxOmomi == omomi){
	            random = (int)Math.random() * 2;
	            if(random == 0){
	                allMaxOmomi = omomi;
	                maxOmomiGridX = gridXTemp;
	                maxOmomiGridY = gridYTemp;
	            }
	        }
	        
	        ret[0] = maxOmomiGridX;
	        ret[1] = maxOmomiGridY;
	        
	        return ret;
	    }
	}
