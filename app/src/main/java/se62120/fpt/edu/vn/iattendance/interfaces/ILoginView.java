package se62120.fpt.edu.vn.iattendance.interfaces;

/**
 * Created by MrDat on 16/03/2018.
 */

public interface ILoginView {

    void navigateToCommit(String username, String token);
    //void network();
    void loginFailed();

}
