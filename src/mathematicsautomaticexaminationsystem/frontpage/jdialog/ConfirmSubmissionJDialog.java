package mathematicsautomaticexaminationsystem.frontpage.jdialog;

import mathematicsautomaticexaminationsystem.frontpage.eventhandler.SubmitHandler;
import mathematicsautomaticexaminationsystem.frontpage.eventhandler.TestStartEventHandler;

import javax.swing.*;
import java.awt.*;


public class ConfirmSubmissionJDialog extends JDialog {

    private static final String MESSAGE = "确认提交试卷吗？！！！";

    public ConfirmSubmissionJDialog(TestStartEventHandler testStartEventHandler) {
        this.setBounds(800,450,450,100);
        this.setModal(true);
        this.setResizable(false);

        JLabel message = new JLabel(MESSAGE);
        message.setFont(new Font("Dialog", Font.BOLD+ Font.ITALIC , 26));
        message.setForeground(Color.PINK);

        JButton confirm = new JButton("confirm");
        confirm.setFont(new Font("Dialog", Font.BOLD+ Font.ITALIC , 20));
        confirm.addActionListener(new SubmitHandler(testStartEventHandler,this));

        Container TestStartFailJDialogContainer = this.getContentPane();
        TestStartFailJDialogContainer.setLayout(new GridLayout(2,1));

        TestStartFailJDialogContainer.add(message);
        TestStartFailJDialogContainer.add(confirm);

        this.setVisible(true);
    }
}

