/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
import java.io.InterruptedIOException;
import java.util.Random;

class MyRobot extends Robot {
  public void move(int act) throws InterruptedException{
          if(act == 0){//right
            rotateRight(30);
            forward(4);
          }else if(act == 1){//left
            rotateLeft(30);
            forward(4);
          }else if(act == 2){//forword
            forward(4);
          }else if(act == 3){//left
            
            forward(8);
          }
  }

    public void getState() throws InterruptedException{
        state = 0;
        if(getColor(LIGHT_C) == BLACK){
          state += 1;
        }
        // if(getColor(LIGHT_B) == BLUE){  
		    //   state += 8;
        //   if(getColor(LIGHT_A) == BLACK){
        //     state += 2;
        //   }
        // }else{
          if(getColor(LIGHT_B) == BLACK){
            state += 2;
          }
          if(getColor(LIGHT_A) == BLACK){
            state += 4;
          }
        // }
  }




      



  public void learn() throws InterruptedException{
    try{
      // System.out.println("出力");
      random = new Random();
      // step1 Q学習する
      // QLearningのインスタンスを作る
      states = 8;//全状態数
      //   左，真ん中，右
      //0 : 000
      //1 : 001
      //2 : 010
      //3 : 011
      //4 : 100
      //5 : 101
      //6 : 110
      //7 : 111
      //8 : 040
      //9 : 041
      //10: 140
      //11: 141 
      // //以上の12状態と定義する
      state = 0; //現在の状態
      action = 4; //行動数
      alpha = 0.5; //学習率
      gamma = 0.5; //割引率
      q1 = new QLearning(states, action, alpha, gamma);
      // q1.showTable(); 
      
      
      int trails = 2000; //強化学習の試行回数
      int steps = 5000; //1試行当たりの最大ステップ数
      
      for(int t = 1 ; t <= trails; t++){//試行回数だけ繰り返し
        //ロボットを初期位置に戻す
        init();
        getState();
      
        for(int s=0;s < steps; s++){//ステップ数だけ繰り返し
      
          //ε-Greedy法により，行動を選択
          double epsilon = 0.4; 
          int act = q1.selectAction(state, epsilon);
          // System.out.print("(" + state % mazeData.getWidth() + "," + state / mazeData.getWidth() + ") " + state + " " + act + " ");
            
          //選択した行動を実行
          int before = state;
          move(act);


          getState();
        

          //新しい状態を観測&報酬を得る
          double reward = 0;
          switch (state){
          case 0://000
            reward = -100;
            break;
          case 1://001
            reward = 20;
            break;
          case 2://010
            reward = 70;
            break;
          case 3://011
            reward = 30;
            break;
          case 4://100
            reward = 20;
            break;
          case 5://101
            reward = 10;
            break;
          case 6://110
            reward = 30;
            break;
          case 7://111
            reward = 20;
            break;
          case 8://040
            reward = 50;
            break;
          case 9://041
            reward = 50;
            break;
          case 10://140
            reward = 50;
            break;
          case 11://141
            reward = 10;
            break;
          default :System.out.println("nothing to match");break;
          }
          if(getColor(LIGHT_A) == GREEN || getColor(LIGHT_B) == GREEN || getColor(LIGHT_C) == GREEN){
            reward = 500;
          } 
          
          
          //Q値を更新
          q1.update(before,act,state,reward);
          
          // もし時間差分を誤差が十分小さくなれば終了
          // if(Math.abs(q1.getTimeDest(before,act,state,reward)) < 0.00000001){
          //   break;
          // }
          // 速度調整＆画面描画
          // delay();
      
        // ゴールに到達すれば終了
        if (isOnGoal()){
          System.out.println("Goal");
          break;
        }
          
        }//1ステップ分
      
      }//1試行回数

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    System.out.println("finish to learn");
    q1.showTable();
    for(int i = 0;i< states;i++){
      System.out.println("q[" + i + "] = " + q1.selectAction(i) );
    }

  }

  /**
   * 実行用関数
   */
  public void run() throws InterruptedException
  {
    try {
      while(!isOnGoal()){

        getState();

        System.out.println(getColor(LIGHT_A) + " " + getColor(LIGHT_B) + " " + getColor(LIGHT_C));
        int act = q1.selectAction(state);
        // System.out.print("(" + state % mazeData.getWidth() + "," + state / mazeData.getWidth() + ") " + state + " " + act + " ");
            
        //選択した行動を実行
        move(act);
        delay();
      
      }

  } catch (Exception e) {
    e.printStackTrace();
    System.exit(-1);
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