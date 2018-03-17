package se62120.fpt.edu.vn.iattendance.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se62120.fpt.edu.vn.iattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageTeacherFragment extends Fragment {


    public MessageTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_teacher, container, false);
    }

}
