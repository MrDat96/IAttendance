package se62120.fpt.edu.vn.iattendance.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se62120.fpt.edu.vn.iattendance.BuildConfig;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.AttendanceConfig;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.models.User;
import se62120.fpt.edu.vn.iattendance.views.activities.NavigationTeacherActivity;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginView;
import se62120.fpt.edu.vn.iattendance.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView, GoogleApiClient.OnConnectionFailedListener {
    public static int GG_SIGN_IN = 1;
    public static String AppTag = "IATTENDANCE";
    public static final String I_ATTENDANCE_PREFERENCES = "IAttendancePrefs";

    @BindView(R.id.edtUsername) EditText _edtUsername;
    @BindView(R.id.edtPassword) EditText _edtPassword;
    @BindView(R.id.btnSignIn) Button _btnSignIn;
    @BindView(R.id.sign_in_button) SignInButton _btnSignInGG;

    LoginPresenter presenter;
    ProgressDialog progressDialog;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        if(BuildConfig.DEBUG) {
            _edtUsername.setText("anhbn@fpt.edu.vn");
            _edtPassword.setText("Abc12345!");
        }
        presenter = new LoginPresenter(this);
        //presenter.initGoogleSignIn(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //showProgressDialog();
        //presenter.checkExistedUser();
    }

    private   void initFCM(String id) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.v(config.AppTag, "Instance token :" + token);
        sendRegistrationToServer(token, id);
    }
    private void sendRegistrationToServer(String token, String id) {
        DatabaseReference dbRef  = FirebaseDatabase.getInstance().getReference();
        User user = new User();
        user.setUserName(id);
        user.setToken(token);
        dbRef.child("users").child(id).setValue(user);
    }

    void redirectNavigation(String id, String username, String token, int role) {
//        String id = "";
//        if (role == AttendanceConfig.ROLE_TEACHER) {
//            id = "AnhBNSE1101";
//        } else {
//            id = "SE62120";
//        }

        initFCM(id);
        Intent navigationAct = new Intent(getApplicationContext(), NavigationTeacherActivity.class);
//        navigationAct.putExtra("role", 1);
        SharedPreferences sharedPreferences = getSharedPreferences(I_ATTENDANCE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("username", username);
        editor.putString("id", id);
        editor.putInt("role", role);
        editor.commit();
        startActivity(navigationAct);
    }

    @OnClick(R.id.btnSignIn)
    public void loginSubmited(View view) {
        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        presenter.attemptLogin(_edtUsername.getText().toString(), _edtPassword.getText().toString());
        //presenter.signOut();
//        checkPlayServices(this);
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        User user = new User();
//        user.setUserId("SE62120");
//        user.setToken("1234567293rjdkasdfsf");
//        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }
    @OnClick(R.id.sign_in_button)
    public void loginWithGoogle(View view) {
        Log.v(config.AppTag, "Login with gg button clicked!");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(presenter.getmGoogleApiClient());
        startActivityForResult(signInIntent, GG_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GG_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.handleSignInResult(result);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void navigateToCommit(String id, String username, String token, int role) {
        progressDialog.dismiss();
        redirectNavigation(id, username, token, role);
    }

    @Override
    public void loginFailed() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishCheckUserExist() {
        hideProgressDialog();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(config.AppTag, "onConnectionFailed:" + connectionResult);
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
    public static boolean checkPlayServices(Context context) {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable
                (context);
        if (resultCode != ConnectionResult.SUCCESS) {
            Log.i(config.AppTag, "This device does not support Google Play Services. " +
                    "Push notifications are not supported");
            return false;
        } else {
            Log.i(config.AppTag, "This device support Google Play Services. ");
        }
        return true;
    }
}
