package se62120.fpt.edu.vn.iattendance.interfaces;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface ITakeAttendance {
    public void onFetchSlotAttendanceSuccess(SlotAttendance slotAttendance);
    public void onFetchSlotAttendanceFail();
}
