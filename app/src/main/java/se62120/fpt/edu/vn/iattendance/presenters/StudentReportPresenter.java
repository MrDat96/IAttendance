package se62120.fpt.edu.vn.iattendance.presenters;

import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportListenser;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportNoticationListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IStudentReportView;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.StudentReportSendNotificationInteractor;

/**
 * Created by MrDat on 25/03/2018.
 */

public class StudentReportPresenter implements IOnFinishedStudentSendReportListenser, IOnFinishedStudentSendReportNoticationListener{

    IStudentReportView view;
    StudentReportSendNotificationInteractor interactorSendNoti;

    public StudentReportPresenter(IStudentReportView view) {
        this.view = view;
        interactorSendNoti = new StudentReportSendNotificationInteractor(this);
    }

    public void sendReport(String from, String to, String topic, String description, int time_table_id) {
        if (description == null || description.equals("")) {
            description = "No des";
        }
        interactorSendNoti.sendNotification(from, to, topic, description);
    }

    @Override
    public void onFinishedStudentSendReportSuccess() {
        view.onStudentReportSuccess();
    }

    @Override
    public void onFinishedStudentSentReportFail() {
        view.onStudentReportFail();
    }

    @Override
    public void onFinishedStudentSendReportNotificationSuccess() {
        view.onStudentReportSuccess();
    }

    @Override
    public void onFinishedStudentSentReportNotificationFail() {
        view.onStudentReportFail();
    }
}
