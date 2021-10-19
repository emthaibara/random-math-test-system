package mathematicsautomaticexaminationsystem.frontpage.testanswer;

import java.util.HashMap;


/**
 *      我这里是一题1分
 *          题目应该有问题 25页一页5题按道理总共应该有125题才对
 *                  所以这里我的总分是125分
 *                  一题一分
 */
public class TestAnswer {

    private static  HashMap<Integer,String> answerMap = new HashMap<>();

    public static int calculate(int count, int a, String operator1, int b, String operator2, int c){
        int answer;

        if (operator1.equals("+")){
            answer = a+b;
        }else {
            answer = a-b;
        }
        if (operator2.equals("-")){
            answer -= c;
        }else {
            answer += c;
        }
        if (answer >= 0){
            answerMap.put(count,Integer.toString(answer));
        }
        return answer;
    }

    public static int getScore(HashMap<Integer,String> answerMap){
        return calculateScore(answerMap);
    }
    private static int calculateScore(HashMap<Integer,String> map){
        int Score = 0;
        for (int i = 1; i < 126; i++) {
            if (map.get(i).equals(answerMap.get(i))){
                Score++;
            }
        }
        return Score;
    }
}
