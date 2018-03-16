package se62120.fpt.edu.vn.iattendance.interfaces;

/**
 * Created by MrDat on 16/03/2018.
 */

public interface OnLoginFinishedListener {
    void onError();
    void onSuccess(String username, String token);
}
