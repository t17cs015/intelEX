import java.io.*;

/**
 * 強化学習によりゴールまでの経路を学習するクラス
 */
public class MazeModel implements Runnable {
	
  /**
   * 強化学習によりゴールまでの経路を学習するオブジェクトを生成する
   * @param mazeFile 迷路データのファイル名
   */
  public MazeModel(String mazeFile)
  {
    // 迷路データを生成
    mazeData = new MazeData(mazeFile);
    // ロボットを生成
    robot = new Robot(mazeData.getSX(), mazeData.getSY());
  }

  /**
   * 実行用関数
   */
  public void run()
  {
    try {
      
      // ここをがんばって作る
      int dirT = 1;//最後に縦方向を保存
      int dirY = 0;//
      // ゴール座標の取得
      int gx = mazeData.getGX();
      int gy = mazeData.getGY();
      
      while (true) {

        // ロボットの現在位置を取得
        int x = robot.getX();
        int y = robot.getY();
        
        // 下に行けるならば下に行く
        if(x < gx && mazeData.get(x+1, y) != MazeData.BLOCK){
          x++;
        }else if(y < gy && mazeData.get(x, y+1) != MazeData.BLOCK){
          y++;
        }


        // if (mazeData.get(x + dirY, y + dirT) != MazeData.BLOCK){
        //   x += dirY;
        //   y += dirT;
        // }
        
        // if(mazeData.get(x+1, y) != MazeData.BLOCK && dir != 3){
        //   x++;
        //   dir = 2;
        // }
        // else if (mazeData.get(x, y+1) != MazeData.BLOCK && dir != 1){
        //   y++;
        //   dir = 0;
        // }
        // else if(mazeData.get(x-1, y) != MazeData.BLOCK && dir != 2){
        //   x--;
        //   dir = 3;
        // }
        // else if(mazeData.get(x, y-1) != MazeData.BLOCK && dir != 0){
        //   y--;
        //   dir = 1;
        // }

        // ロボットの位置座標を更新
        robot.setX(x);
        robot.setY(y);
        
        // 現在の状態を描画する
        mazeView.repaint();

        // 速すぎるので 500msec 寝る
        Thread.sleep(500);

        // もしゴールに到達すれば終了
        if (x == gx && y == gy)
          break;

        // デバッグ用に現在位置を出力
        System.out.println("x = " + x + ", y = " + y);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
  
  /**
   * 描画用のビューを登録
   */
  public void setView(MazeView view)
  {
    mazeView = view;
  }
  
  /**
   * ロボットオブジェクトを取得する
   * @return ロボットオブジェクト
   */
  public Robot getRobot()
  {
    return robot;
  }
  
  /**
   * 迷路データオブジェクトを取得する
   * @return 迷路データオブジェクト
   */
  public MazeData getMazeData()
  {
    return mazeData;
  }
  
  /** 迷路データ */
  private MazeData mazeData = null;
  /** ロボットデータ */
  private Robot robot = null;

  /** 描画用オブジェクト */
  private MazeView mazeView = null;
}
