package se62120.fpt.edu.vn.iattendance.interfaces;

import se62120.fpt.edu.vn.iattendance.interfaces.ILoginView;
import se62120.fpt.edu.vn.iattendance.models.SynchronousLoginInteractor;

/**
 * Created by MrDat on 16/03/2018.
 */

public interface ILoginPresenter {
    void attemptLogin(String username, String password);
}
