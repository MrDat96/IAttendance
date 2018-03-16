package se62120.fpt.edu.vn.iattendance.presenters;

import butterknife.OnClick;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginPresenter;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginView;
import se62120.fpt.edu.vn.iattendance.interfaces.OnLoginFinishedListener;
import se62120.fpt.edu.vn.iattendance.models.AsynchronusLoginInteractor;
import se62120.fpt.edu.vn.iattendance.models.LoginInteractor;
import se62120.fpt.edu.vn.iattendance.models.SynchronousLoginInteractor;

/**
 * Created by MrDat on 16/03/2018.
 */

public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener{
    private ILoginView view;
    //private SynchronousLoginInteractor interactor;
    //private AsynchronusLoginInteractor interactor;
    private LoginInteractor interactor;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        //this.interactor = new SynchronousLoginInteractor();
        //this.interactor = new AsynchronusLoginInteractor();
        this.interactor = new LoginInteractor(this);
    }
    @Override
    public void attemptLogin(String username, String password) {
        //boolean isValid = interactor.validateCredentials(username, password);
        //interactor.validateCredentials(this, username, password);
        //if (isValid) view.navigateToCommit(); else view.loginFailed();

        interactor.credential(username, password);
    }

    @Override
    public void onError() {
        view.loginFailed();
    }

    @Override
    public void onSuccess(String username, String token) {
        view.navigateToCommit(username, token);
    }
}
