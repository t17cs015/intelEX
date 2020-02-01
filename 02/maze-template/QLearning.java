/**
 * Ｑ学習を行うクラス
 */
import java.util.Random;

public class QLearning {
  
  /**
   * Ｑ学習を行うオブジェクトを生成する
   * @param states  状態数
   * @param actions 行動数
   * @param alpha   学習率（0.0〜1.0）
   * @param gamma   割引率（0.0〜1.0）
   */
  public QLearning(int states, int actions, double alpha, double gamma , MazeData mazeData)
  {
	  this.qTable = new double[states][actions];
	  this.alpha = alpha;
	  this.gamma = gamma;
    this.mazeData = mazeData;
  }
  
  /**
   * epsilon-Greedy 法により行動を選択する
   * @param state   現在の状態
   * @param epsilon ランダムに行動を選択する確率（0.0〜1.0）
   * @return 選択された行動番号
   */
  public int selectAction(int state, double epsilon)
  {
    //rand no tuika
    Random random = new Random();
    int rand = random.nextInt(11);
    if(rand <= epsilon * 10){
      return random.nextInt(4);
    }else{
      int max = 0;
      int width = mazeData.getWidth();
      // int[] data = {state - width ,state - 1,state + 1,state + width};

      for(int i = 1;i < 4;i++){
        if(qTable[state][max] < qTable[state][i]){
          max = i;
        }
      }
      return max;
    }
  }
  
  /*
  up: -width
  down: +width
  /**
   * Greedy 法により行動を選択する
   * @param state   現在の状態
   * @return 選択された行動番号
   */
  public int selectAction(int state)
  {
    int max = 0;
    int width = mazeData.getWidth();
    // int[] data = {state - width ,state - 1,state + 1,state + width};
    for(int i = 1;i < 4;i++){
      if(qTable[state][max] < qTable[state][i]){
        max = i;
      }
    }
    return max;
  }
  
  /**
   * Ｑ値を更新する
   * @param before 状態
   * @param action 行動
   * @param after  遷移後の状態
   * @param reward 報酬
   */
  public void update(int before, int action, int after, double reward)
  {
    qTable[before][action] = qTable[before][action] + alpha * (reward + gamma * selectAction(after,gamma) - qTable[before][action]);
    //select action ga tigau 
  }
  
   //フィールド
	private double qTable[][] = null;
	private double alpha = 0;
	private double gamma = 0;	
  private MazeData mazeData = null;

}












