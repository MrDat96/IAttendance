package se62120.fpt.edu.vn.iattendance.TeacherRole;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import se62120.fpt.edu.vn.iattendance.LoginActivity;
import se62120.fpt.edu.vn.iattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleTeacherFragment extends Fragment {

    RVTeacherScheduleAdapter rvAdapter;
    RecyclerView _rvSchedule;
    ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();

    public ScheduleTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((NavigationTeacherActivity) getActivity()).setActionBarTitle("Schedule");
        View fragmentView = inflater.inflate(R.layout.fragment_schedule, container, false);
        _rvSchedule = (RecyclerView) fragmentView.findViewById(R.id.rvSchedule);
        //_rvSchedule.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        _rvSchedule.setLayoutManager(llm);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("slot", "1");
        hashMap.put("subject", "Mobile programming");
        HashMap<String, String> hashMap2 = new HashMap<>();
        HashMap<String, String> hashMap3 = new HashMap<>();
        HashMap<String, String> hashMap4 = new HashMap<>();
        HashMap<String, String> hashMap5 = new HashMap<>();
        HashMap<String, String> hashMap6 = new HashMap<>();
        hashMap2.put("slot", "2");
        hashMap2.put("subject", "Mobile programming 2");
        hashMap3.put("slot", "3");
        hashMap3.put("subject", "Mobile programming 3");
        hashMap4.put("slot", "4");
        hashMap4.put("subject", "Mobile programming 4");
        hashMap5.put("slot", "5");
        hashMap6.put("subject", "Mobile programming 5");
        hashMap6.put("slot", "6");
        hashMap6.put("subject", "Mobile programming 6");
        hashMaps.add(hashMap);
        hashMaps.add(hashMap2);
        hashMaps.add(hashMap3);
        hashMaps.add(hashMap4);
        hashMaps.add(hashMap5);
        hashMaps.add(hashMap6);
        //Toast.makeText(getContext(), "hihi " + hashMaps.size(), Toast.LENGTH_SHORT).show();
        rvAdapter = new RVTeacherScheduleAdapter(hashMaps);
        _rvSchedule.setAdapter(rvAdapter);
        return fragmentView;
    }
}
