package se62120.fpt.edu.vn.iattendance.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.models.DownloadImageTask;

/**
 * Created by MrDat on 14/03/2018.
 */

public class RVTeacherManualTakeAdapter extends RecyclerView.Adapter<RVTeacherManualTakeAdapter.RVViewHolder> {
    ArrayList<HashMap<String ,String >> hashMaps=new ArrayList<>();

    public RVTeacherManualTakeAdapter(ArrayList<HashMap<String, String>> hashMaps) {
        this.hashMaps = hashMaps;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("TEST", "I'M HERE ON CREATE");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manual_taken_attendance_layout_item, parent, false);
        return new RVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.v("TEST", "I'M HERE OB BIND");
        HashMap<String, String> hashMap = hashMaps.get(position);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder._ivAvatarManualTaken.getLayoutParams();
        params.width = 70;
        params.height = 70;
        holder._ivAvatarManualTaken.setLayoutParams(params);
        new DownloadImageTask(holder._ivAvatarManualTaken).execute(hashMap.get("src"));
        holder._tvStudentID.setText(hashMap.get("studentID"));
        holder._tvStudentName.setText(hashMap.get("studentName"));
        holder._tbStatus.setChecked(hashMap.get("status").equals("1"));
    }

    @Override
    public int getItemCount() {
        return hashMaps.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {

        ImageView _ivAvatarManualTaken;
        TextView _tvStudentID;
        TextView _tvStudentName;
        ToggleButton _tbStatus;

        public RVViewHolder(View itemView) {
            super(itemView);
            _ivAvatarManualTaken = (ImageView) itemView.findViewById(R.id.ivAvatarManualTaken);
            _tvStudentID = (TextView) itemView.findViewById(R.id.tvStudentID);
            _tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
            _tbStatus = (ToggleButton) itemView.findViewById(R.id.tbStatus);
        }
    }
}
