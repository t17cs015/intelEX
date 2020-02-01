public void run(){
  try{
    // step1 Q学習する
    // QLearningのインスタンスを作る
    int states = 10; //状態数
    int action = 10; //行動数
    double alpha = 0.5; //学習率
    double gamma = 0.5; //割引率
    QLearning q1 = new QLearning(states, actions, alpha, gamma);

    int trails = 500; //強化学習の試行回数
    int steps = 200; //1試行当たりの最大ステップ数

    for(int t = 1 ; t <= trails; t++){//試行回数だけ繰り返し
      //ロボットを初期位置に戻す
      for(int s=0;s < steps; s++){//ステップ数だけ繰り返し

        //ε-Greedy法により，行動を選択

        //選択した行動を実行

        //新しい状態を観測&報酬を得る

        //Q値を更新

        //もし時間差分を誤差が十分小さくなれば終了
      }
    }




  }