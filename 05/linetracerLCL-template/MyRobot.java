/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
public class MyRobot extends Robot
{
  /**
   * 実行用関数
   */
  public void run() throws InterruptedException
  {
    while (true) {

      // デバッグ用
      System.out.println("A:" + getColor(LIGHT_A) + " B:" + getColor(LIGHT_B) + " C:" + getColor(LIGHT_C));
      
      if(getColor(LIGHT_B) == BLUE)
	  backward(5);
		
      // 右センサの色に応じて分岐
      switch (getColor(LIGHT_A)) {

      case BLACK:
        // 黒を検知 => 右回転 => 前進
        rotateRight(10);
        forward(1);
        break;
        
      case WHITE:
        // 白を検知 => 左回転 => 前進
        rotateLeft(10);
        forward(1);
	break;

      }

      // 速度調整＆画面描画
      delay();

      // ゴールに到達すれば終了
      if (isOnGoal())
        return;
    }
  }

}
