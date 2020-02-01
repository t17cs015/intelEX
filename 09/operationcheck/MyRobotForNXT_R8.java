/**
 * 実機NXT用ロボットクラス
 */
public class MyRobotForNXT_R6 extends Robot
{
    /** leJOS での起動用 main 関数 */
    static void main(String[] args) { 
	try {
	    // 時間計測
	    Long time = System.currentTimeMillis();

	    // ロボットオブジェクトを生成して実行
	    new MyRobotForNXT_R1().run();

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
	int [] q = new int[8];
	q[0] = 0;
	q[1] = 1;
	q[2] = 2;
	q[3] = 1;
	q[4] = 0;
	q[5] = 1;
	q[6] = 3;
	q[7] = 1;


	while (true) {
	    /* 現在の状態を観測 */
		state = 0;
        if(getColor(LIGHT_C) == BLACK){
          state += 1;
        }
        
        if(getColor(LIGHT_B) == BLACK){
          state += 2;
        }
        if(getColor(LIGHT_A) == BLACK){
          state += 4;
		    }
        


	    /* その状態における最適な行動を実行 */
		if(q[state] == 0){//right
            rotateRight(10);
            forward(0.5);
          }else if(q[state] == 1){//left
            rotateLeft(10);
            forward(0.5);
          }else if(q[state] == 2){//forword
            forward(1);
          }else if(q[state] == 3){//left
            rotateRight(10);
            forward(0.2);
          }else if(q[state] == 4){//left
            rotateLeft(10);
            forward(0.2);
          }



	    // delay()メソッドは、Robot.javaに応じていずれかの機能を実行する
	    // 速度調整＆画面描画 (シミュレーター用)
	    // ESCAPEボタン押下時に割り込みを発生させる (実機NXT用)
	    delay();

	    // ゴールに到達すれば終了
	    if (isOnGoal())
		break;
	}
    }

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

}
