package mathematicsautomaticexaminationsystem.frontpage.jdialog;

import javax.swing.*;
import java.awt.*;



public class TestStartFailJDialog extends JDialog {

    private static final String MESSAGE = "小朋友请先填写完整的学生信息！！";

    public TestStartFailJDialog() {
        this.setBounds(800,450,450,100);
        this.setModal(true);
        this.setResizable(false);

        JLabel message = new JLabel(MESSAGE);
        message.setFont(new Font("Dialog", Font.BOLD+ Font.ITALIC , 26));
        message.setForeground(Color.PINK);

        Container TestStartFailJDialogContainer = this.getContentPane();
        TestStartFailJDialogContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        TestStartFailJDialogContainer.add(message);

        this.setVisible(true);
    }
}
