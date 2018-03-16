package se62120.fpt.edu.vn.iattendance.models;

import android.os.Handler;

import se62120.fpt.edu.vn.iattendance.interfaces.IAsynchrousLoginInteractor;
import se62120.fpt.edu.vn.iattendance.interfaces.OnLoginFinishedListener;

/**
 * Created by MrDat on 16/03/2018.
 * Testting only
 */

public class AsynchronusLoginInteractor implements IAsynchrousLoginInteractor {

    @Override
    public void validateCredentials(final OnLoginFinishedListener listener, final String username, String password) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(username.contains("fpt")) {
                    listener.onSuccess("onSuccessTest", "No worry!");
                } else {
                    listener.onError();
                }
            }
        }, 2000);
    }
}
