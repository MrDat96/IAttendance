package se62120.fpt.edu.vn.iattendance.presenters;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.interfaces.IOnGetReportsHistoryListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnReportUpdateStatusListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IReportsView;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.ReportUpdateStatusInteractor;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.ReportsHistoryInteractor;

/**
 * Created by MrDat on 28/03/2018.
 */

public class ReportsPresenter implements IOnGetReportsHistoryListener , IOnReportUpdateStatusListener {
    IReportsView view;

    ReportsHistoryInteractor interactorHistoryReports;
    ReportUpdateStatusInteractor interactorUpdateReport;


    public ReportsPresenter(IReportsView view) {
        this.view = view;
        interactorHistoryReports = new ReportsHistoryInteractor(this);
        interactorUpdateReport = new ReportUpdateStatusInteractor(this);
    }

    public void getAllReports(String token) {
        interactorHistoryReports.getReportsHistory(token);
    }

    public void updateStatus(String token, int messageId, int statusId) {
        interactorUpdateReport.updateReportStatus(token, messageId, statusId);
    }

    @Override
    public void onScheduleSuccessListener(ArrayList<Report> list) {
        view.onSuccessGetReportsHistoryView(list);
    }

    @Override
    public void onFetchScheduleFailListner() {
        view.onFailGetReportsHistoryView();
    }

    @Override
    public void onReportUpdateStatusSuccessful() {
        view.onUpdateReportSuccess();
    }

    @Override
    public void onReportUpdateStatusFail() {
        view.onUpdateReportFail();
    }
}
