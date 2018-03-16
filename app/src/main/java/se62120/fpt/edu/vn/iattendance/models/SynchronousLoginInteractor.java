package se62120.fpt.edu.vn.iattendance.models;

import se62120.fpt.edu.vn.iattendance.interfaces.ILoginInteractor;

/**
 * Created by MrDat on 16/03/2018.
 * Testing only
 */

public class SynchronousLoginInteractor implements ILoginInteractor {

    public  SynchronousLoginInteractor() {}

    public boolean validateCredentials(String username, String password) {
        return username.contains("fpt");
    }

}
