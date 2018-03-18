package se62120.fpt.edu.vn.iattendance.interfaces;

import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface ITakeAttendanceView {
    public void onFetchAttendanceSuccess(SlotAttendance slotAttendance);
    public void onFetchAttendanceFail();

    public void onSaveAttendanceSuccess();
    public void onSaveAttendanceFail();

    public void onUploadScanImagesSuccess();
    public void onUploadScanImagesFail();
}
