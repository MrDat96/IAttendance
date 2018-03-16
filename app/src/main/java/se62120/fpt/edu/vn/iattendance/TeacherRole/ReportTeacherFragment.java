package se62120.fpt.edu.vn.iattendance.TeacherRole;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se62120.fpt.edu.vn.iattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportTeacherFragment extends Fragment {


    public ReportTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_teacher, container, false);
    }

}
