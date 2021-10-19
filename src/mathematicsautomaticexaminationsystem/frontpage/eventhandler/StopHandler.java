package mathematicsautomaticexaminationsystem.frontpage.eventhandler;

import mathematicsautomaticexaminationsystem.frontpage.jdialog.ConfirmSubmissionJDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class StopHandler implements ActionListener {

    private static TestStartEventHandler testStartEventHandler;

    public StopHandler(TestStartEventHandler testStartEventHandler) {
        StopHandler.testStartEventHandler = testStartEventHandler ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConfirmSubmissionJDialog confirmSubmissionJDialog = new ConfirmSubmissionJDialog(testStartEventHandler);
        confirmSubmissionJDialog.setModal(true);
    }
}
