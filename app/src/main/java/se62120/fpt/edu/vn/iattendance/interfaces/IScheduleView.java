package se62120.fpt.edu.vn.iattendance.interfaces;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.models.TimeTable;

/**
 * Created by MrDat on 17/03/2018.
 */

public interface IScheduleView {
    public void onFetchScheduleSuccess(ArrayList<TimeTable> list);
    public void onFetchScheduleFail();
}
