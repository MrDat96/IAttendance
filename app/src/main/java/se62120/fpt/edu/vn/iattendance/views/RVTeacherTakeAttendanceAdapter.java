package se62120.fpt.edu.vn.iattendance.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.models.DownloadImageTask;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.Student;

/**
 * Created by MrDat on 14/03/2018.
 */

public class RVTeacherTakeAttendanceAdapter extends RecyclerView.Adapter<RVTeacherTakeAttendanceAdapter.RVViewHolder> {
//    ArrayList<HashMap<String ,String >> hashMaps=new ArrayList<>();
//
//    public RVTeacherTakeAttendanceAdapter(ArrayList<HashMap<String, String>> hashMaps) {
//        this.hashMaps = hashMaps;
//    }
    SlotAttendance slotAttendance;

    public RVTeacherTakeAttendanceAdapter(SlotAttendance slotAttendance) {
        Log.v(config.AppTag, "Attendances size" + slotAttendance.getAttendances().size());
        this.slotAttendance = slotAttendance;
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
        //HashMap<String, String> hashMap = hashMaps.get(position);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder._ivAvatarManualTaken.getLayoutParams();
        params.width = 70;
        params.height = 70;
        holder._ivAvatarManualTaken.setLayoutParams(params);
        //new DownloadImageTask(holder._ivAvatarManualTaken).execute(hashMap.get("src"));
        Student student = slotAttendance.getAttendances().get(position).getStudent();
        holder._tvStudentID.setText(student.getId());
        holder._tvStudentName.setText(student.getName());
        Picasso.get().load(student.getAvatarSrc()).into(holder._ivAvatarManualTaken);
//        holder._tvStudentID.setText(hashMap.get("studentID"));
//        holder._tvStudentName.setText(hashMap.get("studentName"));
//        holder._tbStatus.setChecked(hashMap.get("status").equals("1"));
    }

    @Override
    public int getItemCount() {
        if (slotAttendance == null) return 0;
        return slotAttendance.getAttendances().size();
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
