/**
 * ロボットクラスの作成例：動作確認用 (駆動系) ロボット
 */
public class MoveRobot extends Robot
{
  /**
   * Lejos での起動用 main 関数
   * @param arg[] コマンドライン引数の配列
   */
  static void main(String[] args)
  {
    try {
	  
	  Long time = System.currentTimeMillis();
      // ロボットオブジェクトを生成して実行
      new MoveRobot().run();
	  time = (System.currentTimeMillis()-time) / 1000;
	  System.out.println("Time = "+time.intValue() + "sec");
      // 5秒待ってから停止
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      ;
    }
  }
  
  /**
   * 実行用関数
   */
  public void run() throws InterruptedException
  {
    while (true) {

      //180度右回転
      rotateRight(180);
      
      // 速度調整＆画面描画
      delay();
	  if (isOnGoal()){
		  System.out.println("Goal!!");
		  break;
	  }
      // 30cm 進む
      forward(30);
      
      // 速度調整＆画面描画
      delay();
	  if (isOnGoal()){
		  System.out.println("Goal!!");
		  break;
	  }
      // 180度左回転
      rotateLeft(180);

      // 速度調整＆画面描画
      delay();
		if (isOnGoal()){
			System.out.println("Goal!!");
			break;
		}
      // 30cm 進む
      forward(30);
      
      // 速度調整＆画面描画
      delay();
	  
	  if (isOnGoal()){
		System.out.println("Goal!!");
		break;
	  }
	  	
    }
  }

}