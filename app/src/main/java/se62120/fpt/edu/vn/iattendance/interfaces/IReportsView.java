package se62120.fpt.edu.vn.iattendance.interfaces;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.models.reports.Report;

/**
 * Created by MrDat on 27/03/2018.
 */

public interface IReportsView {
    public void onSuccessGetReportsHistoryView(ArrayList<Report> list);
    public void onFailGetReportsHistoryView();

    public void onApproveReport(int position);
    public void onRejectReport(int position);

    public void onUpdateReportSuccess();
    public void onUpdateReportFail();
}
