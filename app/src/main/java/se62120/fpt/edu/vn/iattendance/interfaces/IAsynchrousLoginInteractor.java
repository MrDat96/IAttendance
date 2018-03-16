package se62120.fpt.edu.vn.iattendance.interfaces;

/**
 * Created by MrDat on 16/03/2018.
 */

public interface IAsynchrousLoginInteractor {
    public void validateCredentials(OnLoginFinishedListener listener, String username, String password);
}