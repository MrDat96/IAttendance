package se62120.fpt.edu.vn.iattendance.views.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;

public class StudentReportActivity extends AppCompatActivity {

    TimeTable timeTable;

    @BindView(R.id.edtStudentName) EditText _edtUsername;
    @BindView(R.id.edtClass) EditText _edtClass;
    @BindView(R.id.edtTeacher) EditText _edtTeacher;
    @BindView(R.id.edtDate) EditText _edtDate;
    @BindView(R.id.edtTime) EditText _edtTime;
    @BindView(R.id.edtSlot) EditText _edtSlot;
    @BindView(R.id.edtDescription) EditText _edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_report);

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
        _edtUsername.setText(username);
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        if (timeTable != null) {
            _edtClass.setText(timeTable.getStudentGroup().getId());
            _edtTeacher.setText(timeTable.getTeacher().getName());
            _edtDate.setText(sdfr.format(timeTable.getDate()));
            _edtSlot.setText("Slot " + timeTable.getSlot().getId());
            _edtTime.setText(timeTable.getSlot().getStartTime().substring(0,5) + "-"
                                + timeTable.getSlot().getEndTime().substring(0,5));
        }
    }
}
