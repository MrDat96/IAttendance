package se62120.fpt.edu.vn.iattendance.interfaces;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface IOnFinishedUpdateAttendancesLisnter {
    public void OnUpdateAttendanceSuccess(int code, String message);
    public void OnUpdateAttendanceFail(int code, String message);
}
