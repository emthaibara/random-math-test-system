package mathematicsautomaticexaminationsystem.frontpage.eventhandler;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Objects;

/**
 *      这里用于实时更新当前的答题情况
 */
public class AnswerJTestFieldFocusListener implements FocusListener {

    private static HashMap<Integer,JPanel> allTopic;

    public AnswerJTestFieldFocusListener(HashMap<Integer,JPanel> allTopic) {
        AnswerJTestFieldFocusListener.allTopic = allTopic ;
    }

    @Override
    public void focusGained(FocusEvent e) {

    }
    @Override
    public void focusLost(FocusEvent e) {
        JTextField textField = (JTextField) e.getSource();
        int index = Integer.parseInt(textField.getName());
        String text = textField.getText();
        //从allTopic拿到题目
        JPanel topic = Objects.isNull(allTopic.get(index)) ? null : allTopic.get(index);
        if (!Objects.isNull(topic)){
            JTextField textField1 = (JTextField) topic.getComponent(7);
            textField1.setText(text.trim());
        }
    }
}
