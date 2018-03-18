package se62120.fpt.edu.vn.iattendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se62120.fpt.edu.vn.iattendance.views.NavigationTeacherActivity;
import se62120.fpt.edu.vn.iattendance.interfaces.ILoginView;
import se62120.fpt.edu.vn.iattendance.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    public static String AppTag = "IATTENDANCE";
    public static final String I_ATTENDANCE_PREFERENCES = "IAttendancePrefs";

    @BindView(R.id.edtUsername) EditText _edtUsername;
    @BindView(R.id.edtPassword) EditText _edtPassword;
    @BindView(R.id.btnSignIn) Button _btnSignIn;

    LoginPresenter presenter;
    ProgressDialog progressDialog;

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
        //progressBar = new ProgressBar(this, null, ProgressBar.);
    }

    void redirectNavigation(String username, String token) {
        Intent navigationAct = new Intent(getApplicationContext(), NavigationTeacherActivity.class);
//        navigationAct.putExtra("role", 1);
//        navigationAct.putExtra("name", "MrDat");
//        navigationAct.putExtra("email", "datntse62120@fpt.edu.vn");
//        navigationAct.putExtra("token", "123456sdfghjkljh123456");
        SharedPreferences sharedPreferences = getSharedPreferences(I_ATTENDANCE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("username", username);
        editor.commit();
        startActivity(navigationAct);
    }

    @OnClick(R.id.btnSignIn)
    public void loginSubmited(View view) {
        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        presenter.attemptLogin(_edtUsername.getText().toString(), _edtPassword.getText().toString());
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void navigateToCommit(String username, String token) {
        progressDialog.dismiss();
        redirectNavigation(username, token);
    }

    @Override
    public void loginFailed() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
    }
}
