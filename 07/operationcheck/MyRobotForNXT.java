/**
 * 実機NXT用ロボットクラス
 */
public class MyRobotForNXT extends Robot
{
    /** leJOS での起動用 main 関数 */
    static void main(String[] args) { 
	try {
	    // 時間計測
	    Long time = System.currentTimeMillis();

	    // ロボットオブジェクトを生成して実行
	    new MyRobotForNXT().run();

	    time = (System.currentTimeMillis() - time) / 1000;
	    System.out.println("Time = "+time.intValue() + "sec");

	    // 7秒待ってから停止
	    Thread.sleep(7000);
	}catch (InterruptedException e) {
	    ;
	}
    }

    /**
     * 実行用関数
     */
    public void run() throws InterruptedException
    {
	/* 学習した最適政策を表す配列 */
	int [] q = new int[12];
	q[0] = 0;
	q[1] = 0;
	q[2] = 0;
	q[3] = 0;
	q[4] = 0;
	q[5] = 0;
	q[6] = 0;
	q[7] = 0;
	q[8] = 0;
	q[9] = 0;
	q[10] = 0;
	q[11] = 0;


	while (true) {
	    /* 現在の状態を観測 */


	    /* その状態における最適な行動を実行 */


	    // delay()メソッドは、Robot.javaに応じていずれかの機能を実行する
	    // 速度調整＆画面描画 (シミュレーター用)
	    // ESCAPEボタン押下時に割り込みを発生させる (実機NXT用)
	    delay();

	    // ゴールに到達すれば終了
	    if (isOnGoal())
		break;
	}
    }

private Random random;
private int states;//全状態数
//   左，真ん中，右
//0 : 000
//1 : 001
//2 : 010
//3 : 011
//4 : 100
//5 : 101
//6 : 110
//7 : 111
// //以上の8状態と定義する
private int state ; //現在の状態
private int action; //行動数
private double alpha; //学習率
private double gamma; //割引率
private QLearning q1 = null;

}
