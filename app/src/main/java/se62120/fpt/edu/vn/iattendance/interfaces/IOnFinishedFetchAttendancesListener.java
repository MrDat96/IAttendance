package se62120.fpt.edu.vn.iattendance.interfaces;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.models.Attendance;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface IOnFinishedFetchAttendancesListener {
    public void onFetchAttendanceSuccess(ArrayList<Attendance> attendances);
    public void onFetchAttendanceFail();
}
