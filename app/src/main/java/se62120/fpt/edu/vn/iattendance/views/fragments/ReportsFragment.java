package se62120.fpt.edu.vn.iattendance.views.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IReportsView;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;
import se62120.fpt.edu.vn.iattendance.presenters.ReportsPresenter;
import se62120.fpt.edu.vn.iattendance.views.activities.NavigationTeacherActivity;
import se62120.fpt.edu.vn.iattendance.views.adapters.RVReportsHistoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements IReportsView {

    RVReportsHistoryAdapter adapter;
    @BindView(R.id.rvReportsHistory) RecyclerView _rvReportsHistory;

    private String token;
    private int role;

    ArrayList<Report> reports;

    ReportsPresenter presenter;

    ProgressDialog mProgressDialog;

    public ReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((NavigationTeacherActivity) getActivity()).setActionBarTitle("Reports");
        View fragmentView = inflater.inflate(R.layout.fragment_report_teacher, container, false);
        ButterKnife.bind(this, fragmentView);

        presenter = new ReportsPresenter(this);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.share_preference),
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "Not found!");
        role = sharedPreferences.getInt("role", -1);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        _rvReportsHistory.setLayoutManager(llm);

        presenter.getAllReports("bearer " + token);
        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onSuccessGetReportsHistoryView(ArrayList<Report> list) {
        reports = list;
        Log.v(config.AppTag, "On Reports Fragment List size : " + list.size());
        Log.v(config.AppTag, "Fetch Data success");
        adapter = new RVReportsHistoryAdapter(list, role, this);
        _rvReportsHistory.setAdapter(adapter);
    }

    @Override
    public void onFailGetReportsHistoryView() {

    }

    @Override
    public void onApproveReport(int position) {
        Log.v(config.AppTag, "On approve " + position);
        Report report = reports.get(position);
        showProgressDialog();
        presenter.updateStatus("bearer " + token, report.getId(), 1);
    }

    @Override
    public void onRejectReport(int position) {
        Log.v(config.AppTag, "On Reject " + position);
        Report report = reports.get(position);
        showProgressDialog();
        presenter.updateStatus("bearer " + token, report.getId(), 2);
    }

    @Override
    public void onUpdateReportSuccess() {
        hideProgressDialog();
        Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateReportFail() {
        hideProgressDialog();
        Toast.makeText(getContext(), "Update fail", Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this.getContext());
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
}
