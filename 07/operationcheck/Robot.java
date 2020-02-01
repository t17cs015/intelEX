import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
/**
 * ロボットを表す抽象クラス
 */
public abstract class Robot {

  /**
   * ロボットオブジェクトの生成
   */
  public Robot()
  {
    // 各ポートへのセンサ割り当て（第２引数の true はセンサ自体が光を発することを示す）
    lightA = new LightSensor(SensorPort.S1, true);
    lightB = new ColorHTSensor(SensorPort.S2);
    lightC = new LightSensor(SensorPort.S3, true);
    ultraSonic = new UltrasonicSensor(SensorPort.S4);

    // モータの回転スピードの設定 (360 deg/sec)
    Motor.A.setSpeed(degPerSec);
    Motor.C.setSpeed(degPerSec);
  }
  
  /**
   * 実行用関数
   */
  public abstract void run() throws InterruptedException;

  /**
   * 一定時間眠る
   */
  public void delay() throws InterruptedException
  {
    // ESCAPE ボタン押下時に割り込み発生
    if (Button.ESCAPE.isDown())
      throw new InterruptedException();
  }
  
  /**
   * 指定距離前進する
   * @param cm 指定距離(cm)
   */
  public void forward(double cm)
  {
    // 左右のモータを同時に指定角度回転させる（タイヤの直径が 55mm）
    Motor.A.rotate((int)(360 / (55 * Math.PI) * 10 * cm), true);
    Motor.C.rotate((int)(360 / (55 * Math.PI) * 10 * cm), true);
    while (Motor.A.isMoving() || Motor.C.isMoving()) Thread.yield();
  }
  
  /**
   * 指定距離後進する
   * @param cm 指定距離(cm)
   */
  public void backward(double cm)
  {
    forward(-cm);
  }
  
  /**
   * 回転する
   * @param angle 回転角度(度)．時計回転の場合は正の値を，半時計回転の場合は負の値を指定す
   * る
   */
  public void rotate(double angle)
  {
    // 左右のモータをそれぞれ逆回転させる
    // 回転半径が 110mm なので, タイヤを２度回転させると，ロボットが１度回転する
    Motor.A.rotate((int)(-angle * 2), true);
    Motor.C.rotate((int)(+angle * 2), true);
    while (Motor.A.isMoving() || Motor.C.isMoving()) Thread.yield();
  }
  
  /**
   * 右に回転する
   * @param angle 回転角度(度)．正の値を指定する
   */
  public void rotateRight(double angle)
  {
    rotate(angle);
  }
  
  /**
   * 左に回転する
   * @param angle 回転角度(度)．正の値を指定する
   */    
  public void rotateLeft(double angle)
  {
    rotate(-angle);
  }
  
  /**
   * 光センサを使って色を読み取る
   * @param 光センサ番号
   * @return 色番号
   */
  public int getColor(int lightNo)
  {
    int val = 0;
	  
    if(lightNo == LIGHT_A) {
		val = lightA.readNormalizedValue();
		//ライン判定
		if (550 > val) return BLACK;
    //閾値を黒青と白の相谷なるように550に修正
		else  
			return WHITE;
	}
	else if(lightNo == LIGHT_B) {
		val = lightB.getColorID();
		// 色判別
		switch (val) {
			case 2:
				return BLUE; 
			case 7:
				return BLACK;
			default:
		}		return WHITE;
	}
	else {
		val = lightC.readNormalizedValue();
		//ライン判定
		if (550 > val) return BLACK;
    //閾値を黒青と白の相谷なるように550に修正
		else 
			return WHITE;
	}
  
  }
  
    public int getDistance(){ // cm単位
		return ultraSonic.getDistance();
    } 

  /**
   * ゴールに到達したか判定する
   * @return ゴールに到達している場合 true を返す
   */
  public boolean isOnGoal()
  {
    int distance;
    distance = ultraSonic.getDistance();
    if(distance <= 20)
	return true;
    return false;
  }

  /** ライトセンサＡ（左）   */
  public LightSensor lightA = null;
  /** ライトセンサＢ（中央） */
  public ColorHTSensor lightB = null;
  /** ライトセンサＣ（右）   */
  public LightSensor lightC = null;
  /** 超音波センサ */
  public UltrasonicSensor ultraSonic = null;

  /** 光センサＡを表す定数 */
  public final static int LIGHT_A = 0;
  /** 光センサＢを表す定数 */
  public final static int LIGHT_B = 1;
  /** 光センサＣを表す定数 */
  public final static int LIGHT_C = 2;

  /** 白色を表す定数 */
  public final static int WHITE = 0;
  /** 緑色を表す定数 */
  public final static int GREEN = 1;
  /** 黒色を表す定数 */
  public final static int BLACK = 2;
  /** 赤色を示す定数 */
  public final static int RED = 3;
  /** 青色を示す定数 */
  public final static int BLUE = 4;

  /** 不明な色を表す定数 */
  public final static int UNKNOWN_COLOR = 5;

  /** モーターの回転速度 (deg/sec) */
  private final static int degPerSec = 180;
}