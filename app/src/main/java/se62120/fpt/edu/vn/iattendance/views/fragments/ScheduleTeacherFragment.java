package se62120.fpt.edu.vn.iattendance.views.fragments;


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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IScheduleView;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.presenters.SchedulePresenter;
import se62120.fpt.edu.vn.iattendance.views.NavigationTeacherActivity;
import se62120.fpt.edu.vn.iattendance.views.RVTeacherScheduleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleTeacherFragment extends Fragment implements IScheduleView {


    RVTeacherScheduleAdapter rvAdapter;
    @BindView(R.id.rvSchedule) RecyclerView _rvSchedule;
    @BindView(R.id.tvDateTitile)
    TextView _tvDateTile;
    @BindView(R.id.tvWeekTitile) TextView _tvWeekTitile;
    ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
    SchedulePresenter presenter;

    public ScheduleTeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((NavigationTeacherActivity) getActivity()).setActionBarTitle("Schedule");
        View fragmentView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, fragmentView);
       // _rvSchedule = (RecyclerView) fragmentView.findViewById(R.id.rvSchedule);
        //_rvSchedule.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        _rvSchedule.setLayoutManager(llm);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.share_preference),
                Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Not found!");
        //token = "bearer vIU6O2W1GvlS1LfISOk19jRI5iqaN8xwIvODzJXYBYxlIDiswJRtSgrCI09sU0NPoNFnyVX7JUDgte0GB4BsgIKHeg_fhWhTtAydqprnAbOWIUtst75uNE9wEf1XGZEKdCTeOkqkHKGYp9Slzf1K_vR1LPJyNjGnkdnLl66Amw1mV6spFQudLcRvSvE4ov71r97Kn4Y2Vpfw1sZ0ktROxc-nSqkUIsfehvQEh8Yrm70DxO5-I4Fls7pb3O1IPHwoPI-42nSDLStBNBO_mOVyQGwzXLi6PP6-QdNPmKJC7fHNn-JDZtLSi-rp0i9j3-q6vSdNfNjHFbO5kFxB5czcm5Cnz0meCriHH9RJ60Qwr-aSz8RDpKtlu4Nh965SKii986PNPwW14Eeh2VHxS34l8GEbBzYetq-BKBzgJcPGaNMlAdoUlTJniRo477M2Tg_52L0ASnrRByPSGSN-Zf4IxFgUj-ZSflid3PbJP8Mg7r95dhGvrU7KaEpD6Wad4DAa";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.DATE, -1);
        String dateStr = df.format(calendar.getTime());
        Log.v(config.AppTag, "Current time App: " + calendar.getTime().toString());
        _tvDateTile.setText(new SimpleDateFormat("E, dd MMM yyyy").format(calendar.getTime()));

        if (token.equals("Not found!")) {
            Toast.makeText(getContext(), "User need to login!", Toast.LENGTH_SHORT).show();
        } else {
            presenter = new SchedulePresenter(this);
            ArrayList<TimeTable> list = presenter.fetchSchedule(token, dateStr);
            rvAdapter = new RVTeacherScheduleAdapter(list);
            _rvSchedule.setAdapter(rvAdapter);
        }
        return fragmentView;
    }

    public void fetchSchedule() {

    }

    @Override
    public void onFetchScheduleSuccess(ArrayList<TimeTable> list) {
        Log.v(config.AppTag, "Fetch Data success");
    }

    @Override
    public void onFetchScheduleFail() {
        Toast.makeText(getContext(), "Fetch data fail!", Toast.LENGTH_SHORT).show();
    }
}
