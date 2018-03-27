package se62120.fpt.edu.vn.iattendance.interfaces;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.models.reports.Report;

/**
 * Created by MrDat on 28/03/2018.
 */

public interface IOnGetReportsHistoryListener {
    public void onScheduleSuccessListener(ArrayList<Report> list);
    public void onFetchScheduleFailListner();
}
