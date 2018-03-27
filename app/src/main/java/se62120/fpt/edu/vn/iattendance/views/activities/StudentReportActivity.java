package se62120.fpt.edu.vn.iattendance.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.AttendanceConfig;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IScheduleView;
import se62120.fpt.edu.vn.iattendance.interfaces.IStudentReportView;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.models.fcm.Data;
import se62120.fpt.edu.vn.iattendance.models.fcm.FirebaseCloudMessage;
import se62120.fpt.edu.vn.iattendance.presenters.StudentReportPresenter;
import se62120.fpt.edu.vn.iattendance.services.FirebaseService;

public class StudentReportActivity extends AppCompatActivity implements IStudentReportView {

    TimeTable timeTable;
    String userId;

    @BindView(R.id.edtStudentName) EditText _edtUsername;
    @BindView(R.id.edtClass) EditText _edtClass;
    @BindView(R.id.edtTeacher) EditText _edtTeacher;
    @BindView(R.id.edtDate) EditText _edtDate;
    @BindView(R.id.edtTime) EditText _edtTime;
    @BindView(R.id.edtSlot) EditText _edtSlot;
    @BindView(R.id.edtDescription) EditText _edtDescription;
    @BindView(R.id.edtTopic) EditText _edtTopic;

    ProgressDialog mProgressDialog;

    StudentReportPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_report);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            timeTable = (TimeTable) extras.getSerializable("TimeTable");
            Log.v(config.AppTag, "Time Table : " + timeTable);
        } else {
            Toast.makeText(getApplicationContext(), "No extra", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.share_preference), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "No name");
        userId = sharedPreferences.getString("id",null);
        _edtUsername.setText(username);
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        if (timeTable != null) {
            _edtClass.setText(timeTable.getStudentGroup().getId());
            _edtTeacher.setText(timeTable.getTeacher().getName());
            _edtDate.setText(sdfr.format(timeTable.getDate()));
            _edtSlot.setText("Slot " + timeTable.getSlot().getId());
            _edtTime.setText(timeTable.getSlot().getStartTime().substring(0,5) + "-"
                                + timeTable.getSlot().getEndTime().substring(0,5));
            _edtTopic.setText("Missing Checking attendance");
        }
        presenter = new StudentReportPresenter(this);
    }

    @OnClick(R.id.btnSendReport)
    void onSendReport(View view) {
        if (userId == null) {
            Toast.makeText(getApplicationContext(), "Something wrong with your store ID on share references", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog();
            presenter.sendReport(userId, timeTable.getTeacher().getId(), _edtTopic.getText().toString(), _edtDescription.getText().toString(), timeTable.getId());
        }
    }


    @Override
    public void onStudentReportSuccess() {
        hideProgressDialog();
        Toast.makeText(getApplicationContext(), "Send successful", Toast.LENGTH_SHORT).show();
        // Show reports
        
    }

    @Override
    public void onStudentReportFail() {
        hideProgressDialog();
        Toast.makeText(getApplicationContext(), "Send fail", Toast.LENGTH_SHORT).show();
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
    // Back button enable
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
