package mathematicsautomaticexaminationsystem.frontpage.eventhandler;

import mathematicsautomaticexaminationsystem.frontpage.jdialog.ConfirmSubmissionJDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class SubmitHandler implements ActionListener {

    private static TestStartEventHandler testStartEventHandler;

    //这里的路径记得修改一下，我没有动态生成
    private static final String FILEPATH = "src/mathematicsautomaticexaminationsystem/frontpage/eventhandler/File/成绩.txt";
    private static ConfirmSubmissionJDialog confirmSubmissionJDialog;

    public SubmitHandler(TestStartEventHandler testStartEventHandler, ConfirmSubmissionJDialog confirmSubmissionJDialog) {
        SubmitHandler.testStartEventHandler = testStartEventHandler;
        SubmitHandler.confirmSubmissionJDialog = confirmSubmissionJDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File(FILEPATH);
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            System.out.println(testStartEventHandler.getStudentData());
            bufferedOutputStream.write(testStartEventHandler.getStudentData().getBytes(StandardCharsets.UTF_8));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        testStartEventHandler.recycleAndCleanUp();
        confirmSubmissionJDialog.setVisible(false);
    }
}
