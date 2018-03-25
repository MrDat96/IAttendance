package se62120.fpt.edu.vn.iattendance.presenters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginPresenter;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginView;
import se62120.fpt.edu.vn.iattendance.interfaces.OnLoginFinishedListener;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.LoginInteractor;

/**
 * Created by MrDat on 16/03/2018.
 */

public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener, GoogleApiClient.OnConnectionFailedListener {
    private ILoginView view;
    private LoginInteractor interactor;

    public LoginPresenter(ILoginView view) {
        this.view = view;
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
    public void onSuccess(String username, String token, int role) {
        view.navigateToCommit(username, token, role);
    }

    GoogleApiClient mGoogleApiClient;


    public void initGoogleSignIn(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void checkExistedUser() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Nếu dữ liệu của người dùng trong bộ d dệm hợp lệ, OptionalPendingResult sẽ ở trạng thái "done"
            // và GoogleSignInResult sẽ có ngay mà không cần thực hiện đăng nhập lại.
            Log.d(config.AppTag, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            view.onFinishCheckUserExist();
        } else {
            // Nếu người dùng chưa từng đăng nhập trước đó, hoặc phiên làm việc đã hết hạn,
            // thao tác bất đồng bộ này sẽ ngầm đăng nhập người dùng, và thực hiện thao tác cross sign-on.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    view.onFinishCheckUserExist();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(config.AppTag, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Đã đăng nhập thành công, hiển thị trạng thái đăng nhập.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(config.AppTag, "Account token : " + acct.getIdToken());
            //mStatusTextView.setText(acct.getDisplayName());
        } else {
            // Đã đăng xuất, hiển thị trạng thái đăng xuất.
            //mStatusTextView.setText("Signed out");
        }
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.v(config.AppTag, "Signed out");
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }
}
