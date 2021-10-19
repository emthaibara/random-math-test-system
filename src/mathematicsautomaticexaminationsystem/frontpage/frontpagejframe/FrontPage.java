package mathematicsautomaticexaminationsystem.frontpage.frontpagejframe;

import mathematicsautomaticexaminationsystem.frontpage.eventhandler.TestStartEventHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class FrontPage extends JFrame {

    private static final int RESERVED = 12;

    private final JPanel topDataPanel;

    private final JPanel timerPanel;

    private final JPanel testQuestionsPanel;

    private final JPanel turnPagePanel;

    public FrontPage(String Title) throws HeadlessException {

        Container container = this.getContentPane();
        container.setLayout(null);

        //自定义窗体的title
        this.setTitle(Title);

        //禁止拉伸
        this.setResizable(false);

        //设置窗口初始位置，以及初始大小
        this.setBounds(500,200,1000,800);

        //选择JFrame默认的窗口关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        topDataPanel = new JPanel();
        topDataPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topDataPanel.setBounds(0,0,1000,50);
        //topDataPanel.setBackground(Color.CYAN);

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        timerPanel.setBounds(0,50,1000,50);
        //timerPanel.setBackground(Color.BLUE);

        testQuestionsPanel = new JPanel();
        testQuestionsPanel.setLayout(new CardLayout());     //由题目规定 CardLayout
        testQuestionsPanel.setBounds(0,100,1000,630);
        //testQuestionsPanel.setBackground(Color.darkGray);

        turnPagePanel = new JPanel();
        turnPagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        turnPagePanel.setBounds(0,730,1000,70);
        //turnPagePanel.setBackground(Color.MAGENTA);
        //初始化窗口顶部待填写信息
        this.topDataInInitialization();

        this.add(topDataPanel);
        this.add(timerPanel);
        this.add(testQuestionsPanel);
        this.add(turnPagePanel);
        //设置窗口可见
        this.setVisible(true);
    }

    /**
     * 初始化窗口顶部待填写信息
     */
    private void topDataInInitialization(){
        JLabel name =new JLabel("Name:");
        JTextField nameForReserved =new JTextField(RESERVED);

        JLabel specialty =new JLabel("Specialty:");
        JTextField specialtyForReserved =new JTextField(RESERVED);

        JLabel classData =new JLabel("Class:");
        JTextField classDataForReserved=new JTextField(RESERVED);

        JButton testStart = new JButton("StartExam");

        testStart.addActionListener(new TestStartEventHandler(testQuestionsPanel,timerPanel,turnPagePanel,testStart,
                        Arrays.asList(nameForReserved,specialtyForReserved,classDataForReserved)));

        topDataPanel.add(name);
        topDataPanel.add(nameForReserved);
        topDataPanel.add(specialty);
        topDataPanel.add(specialtyForReserved);
        topDataPanel.add(classData);
        topDataPanel.add(classDataForReserved);
        topDataPanel.add(testStart);
    }

}
