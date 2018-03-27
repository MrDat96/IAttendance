package se62120.fpt.edu.vn.iattendance.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.interfaces.IActionReportTeacher;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;

public class DisplayReportsActivity extends AppCompatActivity implements IActionReportTeacher{

    @BindView(R.id.rvReportsHistory)
    RecyclerView _rvReporstHistory;

    ArrayList<Report> reports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_reports);
        ButterKnife.bind(this);



    }

    @Override
    public void onTeacherApprove(int position) {

    }

    @Override
    public void onTeacherReject(int position) {

    }
}
