package se62120.fpt.edu.vn.iattendance.presenters;

import android.util.Log;

import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportListenser;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportNoticationListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IStudentReportView;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.SendReportInteractor;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.StudentReportSendNotificationInteractor;

/**
 * Created by MrDat on 25/03/2018.
 */

public class StudentReportPresenter implements IOnFinishedStudentSendReportListenser, IOnFinishedStudentSendReportNoticationListener{

    IStudentReportView view;
    StudentReportSendNotificationInteractor interactorSendNoti;
    SendReportInteractor interactorSendReport;

    public StudentReportPresenter(IStudentReportView view) {
        this.view = view;
        interactorSendNoti = new StudentReportSendNotificationInteractor(this);
        interactorSendReport = new SendReportInteractor(this);
    }

    public void sendReport(String token,String from, String to, String topic, String description, int time_table_id) {
        if (description == null || description.equals("")) {
            description = "No des";
        }
        interactorSendNoti.sendNotification(from, to, topic, description);

        String data = convertToJSON(topic, description);
        interactorSendReport.sendReportToTeacher(token, time_table_id +"", data);
    }

    private String convertToJSON(String topic, String description) {
        String data = "{"
                + "\"topic\":" + "\"" + topic + "\","
                + "\"description\":" + "\"" + description + "\""
                + "}";
        return data;
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
        Log.v(config.AppTag, "Send noti successful");
    }

    @Override
    public void onFinishedStudentSentReportNotificationFail() {
        Log.v(config.AppTag, "Send noti fail");
    }
}
