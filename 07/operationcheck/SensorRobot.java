/**
 * ロボットクラスの作成例：動作確認用 (センサー系) ロボット
 */

import lejos.nxt.*;


public class SensorRobot extends Robot
{
/** Lejos での起動用 main 関数
 * @param arg[] コマンドライン引数の配列
 **/
  public static void main (String[] args) 
  {
	  try{
		  Long time = System.currentTimeMillis();
		  // ロボットオブジェクトを生成して実行
		  new SensorRobot().run();
		  time = (System.currentTimeMillis()-time) / 1000;
		  System.out.println("Time = "+time.intValue() + "sec");
		  //3秒待ってから停止
		  Thread.sleep(3000);
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
			
			if (Button.ESCAPE.isDown())
				break;
		
			LCD.drawString("A: "+getColor(LIGHT_A), 0, 0);
			LCD.drawString("B: "+getColor(LIGHT_B), 0, 1);
			LCD.drawString("C: "+getColor(LIGHT_C), 0, 2);
					
				
			
		}
	}
	
}
