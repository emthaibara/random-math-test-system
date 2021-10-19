package mathematicsautomaticexaminationsystem.frontpage.eventhandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ToNextOrPreHandler {

    private static int numberOfPages = 1;

    private static final int LAST_PAGE = 25;

    private static final int FIRST_PAGE = 1;

    private static JButton nextPage;

    private static JButton prePage;

    private static JButton firstPage;

    private static JButton lastPage;

    private static JPanel testQuestionsPanel;

    public ToNextOrPreHandler(JButton nextPage, JButton prePage,JButton firstPage,JButton lastPage,JPanel testQuestionsPanel) {
        ToNextOrPreHandler.nextPage = nextPage;
        ToNextOrPreHandler.prePage = prePage;
        ToNextOrPreHandler.firstPage = firstPage;
        ToNextOrPreHandler.lastPage = lastPage;
        ToNextOrPreHandler.testQuestionsPanel = testQuestionsPanel;
        addActionListener();
    }

    private static void addActionListener(){
        nextPage.addActionListener(new ToNextOrPreHandler.NextHandler());
        prePage.addActionListener(new ToNextOrPreHandler.PreHandler());
        firstPage.addActionListener(new ToNextOrPreHandler.FirstPageHandler());
        lastPage.addActionListener(new ToNextOrPreHandler.LastPageHandler());
    }

    private static void renew(){
        CardLayout cardLayout = (CardLayout) testQuestionsPanel.getLayout();
        System.out.println(numberOfPages);
        cardLayout.show(testQuestionsPanel,Integer.toString(numberOfPages));
    }

    private static class FirstPageHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            numberOfPages = 1;
            nextPage.setEnabled(true);
            renew();
        }
    }

    private static class LastPageHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            numberOfPages = 25;
            prePage.setEnabled(true);
            renew();
        }
    }

    private static class NextHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (numberOfPages == LAST_PAGE){
                nextPage.setEnabled(false);
            }else {
                nextPage.setEnabled(true);
                numberOfPages++;
                System.out.println("NextHandler"+numberOfPages);
                renew();
            }
        }
    }

     private static class PreHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (numberOfPages == FIRST_PAGE){
                prePage.setEnabled(false);
            }else {
                prePage.setEnabled(true);
                numberOfPages--;
                System.out.println("PreHandler"+numberOfPages);
                renew();
            }
        }
    }
}
