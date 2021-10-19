package mathematicsautomaticexaminationsystem.frontpage.eventhandler;

import mathematicsautomaticexaminationsystem.frontpage.jdialog.TestStartFailJDialog;
import mathematicsautomaticexaminationsystem.frontpage.testanswer.TestAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 *
 *                 private HashMap<Integer,List<JPanel>> topicMap = new HashMap<>();
 *                 流式布局topicPanel
 *                 label1 Operator label2 Operator label3 = JTextField4
 *
 *                 ｜—— —— ——｜     |—— —— ——｜     |—— —— ——｜      |—— —— —— —— ——|
 *                 ｜ Label1 ｜     | Label2 ｜     | Label3 ｜  =   |  JTextField4 |
 *                 ｜—— —— ——｜     |—— —— ——｜     |—— —— ——｜      |—— —— —— —— ——|
 *             然后添加进testQuestionsPanel ---->这里可以有两个方案
 *                         1)只要翻页一次就重写new一个testQuestionsPanel
 *                         2)只要翻页一次就去修改testQuestionsPanel里的组件
 *                                 这里我更倾向于方案二
 */
public class TestStartEventHandler implements ActionListener {

    private static final String[] OPERATOR = new String[]{"+","-"};

    private final Random random;

    private final HashMap<Integer,JPanel> allTopic;

    private final JPanel timerPanel;

    private final JPanel testQuestionsPanel;

    private final JPanel turnPagePanel;

    private final JButton testStart;

    private final List<JTextField> studentDataJTextField;

    private TestStartFailJDialog testStartFailJDialog ;

    public TestStartEventHandler(JPanel testQuestionsPanel,JPanel timerPanel,JPanel turnPagePanel,JButton testStart,List<JTextField> studentDataJTextField) {
        this.testQuestionsPanel = testQuestionsPanel;
        this.studentDataJTextField = studentDataJTextField;
        this.turnPagePanel = turnPagePanel;
        this.testStart = testStart;
        this.timerPanel = timerPanel ;

        allTopic = new HashMap<>();
        random = new Random();
    }

    /**
     * 事件监听------>
     *          TestStart后
     *                 1.首先需要做的是检查信息填写是否完整，并存入 List 中
     *                 2.如果不完整则弹窗提示,否则就开始随机生成算数题
     *                      生成算数题的方案有两种：
     *                              1)start之后直接生成50道,后续翻页通过索引从Map中去拿。
     *                              2)翻页一次生成一次,然后把题目put进Map中,设置索引,当用户翻前一页的时候去Map中取。
     *                                      我更倾向于方案1),以下的实现基于方案 1
     *           **以及计时器的设计：
     *                  详情请见
     *                  @see TestStartEventHandler#countdownStart()
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //trim()用于去除String前后的空格
        long count = studentDataJTextField.stream()
                        .filter(string -> string.getText().trim().equals(""))
                        .count();
        if (count != 0){
            if (Objects.isNull(testStartFailJDialog)){
                testStartFailJDialog = new TestStartFailJDialog();
            }else {
                testStartFailJDialog.setVisible(true);
            }
        }else {
            //将JTextField设置为不可更改
            studentDataJTextField.forEach(TextField -> TextField.setEditable(false));

            //将Start按钮设置为不可点击显灰色
            testStart.setEnabled(false);

            //接下来首先把25*5道数学题随机生成出来， ----> 需要注意的是出的题的答案不能为负数！！
            creatTopicMap();
            testQuestionsFrontPage();

            //添加 首页，上一页，下一页，尾页按钮
            addTurnPageButton();

            //最后再开始倒计时的构造
            countdownStart();
            creatStopJButton();
        }
    }

    private void testQuestionsFrontPage(){
        int count = 1;
        for (int i = 1; i < 26; i++) {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayout(5,1));
            for (int j = 1 ; j < 6 ; j++){
                jPanel.add(allTopic.get(count++));
            }
            testQuestionsPanel.add(jPanel,Integer.toString(i));
        }
        CardLayout cardLayout = (CardLayout) testQuestionsPanel.getLayout();
        cardLayout.show(testQuestionsPanel,"1");
    }

    private void addTurnPageButton() {
        JButton firstPage = new JButton("First");
        firstPage.setFont(new Font("", Font.BOLD+ Font.ITALIC , 12));

        JButton prePage = new JButton("Pre");
        prePage.setFont(new Font("", Font.BOLD+ Font.ITALIC , 12));

        JButton nextPage = new JButton("Next");
        nextPage.setFont(new Font("", Font.BOLD+ Font.ITALIC , 12));

        JButton lastPage = new JButton("TheLast");
        lastPage.setFont(new Font("", Font.BOLD+ Font.ITALIC , 12));

        new ToNextOrPreHandler(nextPage,prePage,firstPage,lastPage,testQuestionsPanel);

        turnPagePanel.add(firstPage);
        turnPagePanel.add(prePage);
        turnPagePanel.add(nextPage);
        turnPagePanel.add(lastPage);
    }


    private void creatStopJButton() {
        JButton stopJButton = new JButton("Carry out an Assignment");
        stopJButton.setFont(new Font("", Font.BOLD+ Font.ITALIC , 12));
        stopJButton.addActionListener(new StopHandler(this));
        timerPanel.add(stopJButton);
    }

    private void creatTopicMap(){
        //25*5 一页五题
        for (int i = 1; i <= 25*5; i++) {
            allTopic.put(i,generateTopicPanel());
        }
    }
    private int count = 1 ;
    private JPanel generateTopicPanel(){
        int number1;
        int number2;
        int number3;
        String strOperator1;
        String strOperator2;

        //筛选出答案不为负数的 题目
        do{
            number1 = (getRandomInteger(0,100));
            number2 = (getRandomInteger(0,100));
            number3 = (getRandomInteger(0,100));
            strOperator1 = getRandomOperator();
            strOperator2 = getRandomOperator();
        }while (TestAnswer.calculate(count,number1,strOperator1,number2,strOperator2,number3) < 0);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel index = new JLabel(count +")   ");
        JLabel label1 = new JLabel(Integer.toString(number1));
        JLabel label2 = new JLabel(Integer.toString(number2));
        JLabel label3 = new JLabel(Integer.toString(number3));
        JLabel equal = new JLabel(" = ");
        JLabel operator1 = new JLabel(strOperator1);
        JLabel operator2 = new JLabel(strOperator2);
        JTextField textField = new JTextField(6);
        textField.setName(Integer.toString(count++));
        textField.addFocusListener(new AnswerJTestFieldFocusListener(allTopic));
        jPanel.add(index);
        jPanel.add(label1);
        jPanel.add(operator1);
        jPanel.add(label2);
        jPanel.add(operator2);
        jPanel.add(label3);
        jPanel.add(equal);
        jPanel.add(textField);

        return jPanel;
    }

    /**
     *      倒计时
     */
    private static int min = 90;
    private static int s = 0;
    private void countdownStart(){
        JLabel minJLabel = new JLabel();
        timerPanel.add(minJLabel);
        Thread countdown = new Thread(() -> {
            for (int i = 0; i <= 90*60; i++) {
                try {
                    Thread timeThread = new Thread(() -> {
                        minJLabel.setText(min+" min : "+s+" s");
                        if (s == 0){
                            min -- ;
                            s = 59 ;
                        }else {
                            s--;
                        }
                    });
                    timeThread.start();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        countdown.start();
    }

    private int getRandomInteger(int a,int b){
        //(数据类型)(a+Math.random()*(b-a+1)) 随机数公示区间[a,b]
        return  (int)(a+random.nextDouble()*(b-a+1));
    }

    private String getRandomOperator(){
        return OPERATOR[getRandomInteger(0,1)];
    }


    /**
     * 返回一个学生data
     */
    public String getStudentData(){
        String[] studentData = new String[4];
        studentData[0] = studentDataJTextField.get(0).getText();
        studentData[1] = studentDataJTextField.get(1).getText();
        studentData[2] = studentDataJTextField.get(2).getText();
        studentData[3] = String.valueOf(TestAnswer.getScore(getAnswerMap()));
        return "    Name:"+studentData[0]+"    Specialty:"+studentData[1]+"    Class:"+studentData[2]+"    Score:"+studentData[3];
    }

    /**
     *返回一个学生答案 Map
     */
    private HashMap<Integer,String> getAnswerMap(){
        HashMap<Integer,String> map = new HashMap<>();
        for (int i = 1; i <= 25*5; i++) {
            JPanel jPanel = allTopic.get(i);
            JTextField jTextField = (JTextField) jPanel.getComponent(7);
            map.put(i,jTextField.getText());
        }
        return map;
    }

    /**
     *  stop后的组件清理,对象回收
     */
    public void recycleAndCleanUp(){
        this.allTopic.clear();

        this.timerPanel.removeAll();
        this.testQuestionsPanel.removeAll();
        this.turnPagePanel.removeAll();

        this.timerPanel.repaint();
        this.testQuestionsPanel.repaint();
        this.turnPagePanel.repaint();

        this.testStart.setEnabled(true);

        this.count = 1 ;
        min = 90 ;
        s = 0 ;

    }
}


