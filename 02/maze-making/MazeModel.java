import java.io.*;
import java.util.Random;

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
    Random random = new Random();
    // step1 Q学習する
    // QLearningのインスタンスを作る
    int states = mazeData.getWidth() * mazeData.getHeight(); //状態数
    int state = 0;//現在の状態
    int action = 4; //行動数
    // up:0,left:1,right:2,down:3
    double alpha = 0.5; //学習率
    double gamma = 0.5; //割引率
    QLearning q1 = new QLearning(states, action, alpha, gamma , mazeData);

    int trails = 500; //強化学習の試行回数
    int steps = 200; //1試行当たりの最大ステップ数

    for(int t = 1 ; t <= trails; t++){//試行回数だけ繰り返し
      //ロボットを初期位置に戻す
      state = mazeData.getSX() + mazeData.getSY() * mazeData.getWidth() ;//first plase
      robot.setX(mazeData.getSX());
      robot.setY(mazeData.getSY());

      for(int s=0;s < steps; s++){//ステップ数だけ繰り返し

        //ε-Greedy法により，行動を選択
        double epsilon = 0.3; 
        int act = q1.selectAction(state, epsilon);
        // System.out.println(state % mazeData.getWidth() + "=" +robot.getX() + "," + state / mazeData.getWidth() + "=" + robot.getY() );
        // System.out.print("(" + state % mazeData.getWidth() + "," + state / mazeData.getWidth() + ") " + state + " " + act + " ");
          
        //選択した行動を実行
        int before = state;
        if(act == 0){//up
          if(robot.getY() > 0){
            state -= mazeData.getWidth();
            robot.setY(robot.getY()-1);
          }
        }else if(act == 1){//left
          if(robot.getX() > 0){
            state -= 1;
            robot.setX(robot.getX()-1);
          }
        }else if(act == 2){//right
          if(robot.getX() < mazeData.getWidth()-1){
            state += 1;
            robot.setX(robot.getX()+1);
          }
        }else if(act == 3){//down
          if(robot.getY() < mazeData.getHeight()-1){
              state += mazeData.getWidth();
              robot.setY(robot.getY()+1);
          }
        }
        

        //新しい状態を観測&報酬を得る
        double reward = 0;
        switch (mazeData.get(state % mazeData.getWidth(),state / mazeData.getWidth())){//(x,y)'s get
        case 0:
          reward = -3;
          // System.out.println("yuka");
          break;
        case 1:
          reward = -100;
          // System.out.println("wall");
          break;
        case 2:
          reward = -3;
          // System.out.println("start");
          break;
        case 3:
          reward = 100;
          // System.out.println("goal");
          break;
        default :System.out.println("nothing to match");break;
        }

        //Q値を更新
        q1.update(before,act,state,reward);

        //もし時間差分を誤差が十分小さくなれば終了
        if(Math.abs(q1.getTimeDest(before,act,state,reward)) < 0.00001){
          break;
        }
      }
    }
      // System.out.println("finish to learn");
      // q1.showTable();

      //reset for move
      state = mazeData.getSX() + mazeData.getSY() * mazeData.getWidth() ;//first plase
      robot.setX(mazeData.getSX());
      robot.setY(mazeData.getSY());
      System.out.println("(x,y) state action");
      System.out.println("(" + state % mazeData.getWidth() + "," + state / mazeData.getWidth() + ") " + state);

      // move for goal
      while(robot.getX() != mazeData.getGX() || robot.getY() != mazeData.getGY()){
        //Greedy法により，行動を選択
        int act = q1.selectAction(state);
        // System.out.println(state % mazeData.getWidth() + "=" +robot.getX() + "," + state / mazeData.getWidth() + "=" + robot.getY() );
          
        //選択した行動を実行
        int before = state;
        if(act == 0){//up
          if(robot.getY() > 0){
            state -= mazeData.getWidth();
            robot.setY(robot.getY()-1);
          }
        }else if(act == 1){//left
          if(robot.getX() > 0){
            state -= 1;
            robot.setX(robot.getX()-1);
          }
        }else if(act == 2){//right
          if(robot.getX() < mazeData.getWidth()-2){
            state += 1;
            robot.setX(robot.getX()+1);
          }
        }else if(act == 3){//down
          if(robot.getY() < mazeData.getHeight()-2){
              state += mazeData.getWidth();
              robot.setY(robot.getY()+1);
          }
        }

                
        // 現在の状態を描画する
        mazeView.repaint();

        // 速すぎるので 500msec 寝る
        Thread.sleep(500);


        // デバッグ用に現在位置を出力
        System.out.println("(" + state % mazeData.getWidth() + "," + state / mazeData.getWidth() + ") " + state + " " + act + " ");
        // System.out.println("x = " + robot.getX() + ", y = " + robot.getY());
      
      };

    
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

      
      // // ここをがんばって作る
      // int dirT = 1;//最後に縦方向を保存
      // int dirY = 0;//
      // // ゴール座標の取得
      // int gx = mazeData.getGX();
      // int gy = mazeData.getGY();
      
      // while (true) {

      //   // ロボットの現在位置を取得
      //   int x = robot.getX();
      //   int y = robot.getY();
        
      //   // 下に行けるならば下に行く
      //   if(x < gx && mazeData.get(x+1, y) != MazeData.BLOCK){
      //     x++;
      //   }else if(y < gy && mazeData.get(x, y+1) != MazeData.BLOCK){
      //     y++;
      //   }


      //   // ロボットの位置座標を更新
      //   robot.setX(x);
      //   robot.setY(y);
        
      //   // 現在の状態を描画する
      //   mazeView.repaint();

      //   // 速すぎるので 500msec 寝る
      //   Thread.sleep(500);

      //   // もしゴールに到達すれば終了
      //   if (x == gx && y == gy)
      //     break;

      //   // デバッグ用に現在位置を出力
      //   System.out.println("x = " + x + ", y = " + y);
      // }