package se62120.fpt.edu.vn.iattendance.views;

import android.graphics.Color;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.AttendanceConfig;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.models.AttendanceStatus;
import se62120.fpt.edu.vn.iattendance.models.DownloadImageTask;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.Student;

/**
 * Created by MrDat on 14/03/2018.
 */

public class RVTeacherTakeAttendanceAdapter extends RecyclerView.Adapter<RVTeacherTakeAttendanceAdapter.RVViewHolder> {

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
        AttendanceStatus attendanceStatus = slotAttendance.getAttendances().get(position).getStatus();
        holder._tvStudentID.setText(student.getId());
        holder._tvStudentName.setText(student.getName());
        Picasso.get().load(student.getAvatarSrc()).into(holder._ivAvatarManualTaken);

        if (attendanceStatus.getId() == AttendanceConfig.ABSENT_ATTENDANCE
                || attendanceStatus.getId() == AttendanceConfig.NOT_YET_ATTENDANCE) {
            holder._tgbtnStatus.setChecked(false);
            holder._tgbtnStatus.setTextColor(Color.RED);
        } else {
            holder._tgbtnStatus.setChecked(true);
            holder._tgbtnStatus.setTextColor(Color.rgb(100, 221, 23));
        }
        holder._tgbtnStatus.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (slotAttendance == null) return 0;
        return slotAttendance.getAttendances().size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //        @BindView(R.id.ivAvatarManualTaken) ImageView _ivAvatarManualTaken;
//        @BindView(R.id.tvStudentID) TextView _tvStudentID;
//        @BindView(R.id.tvStudentName) TextView _tvStudentName;
//        @BindView(R.id.tgbtnStatus) ToggleButton _tgbtnStatus;
        ImageView _ivAvatarManualTaken;
        TextView _tvStudentID;
        TextView _tvStudentName;
        ToggleButton _tgbtnStatus;

        public RVViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(itemView);
            _ivAvatarManualTaken = (ImageView) itemView.findViewById(R.id.ivAvatarManualTaken);
            _tvStudentID = (TextView) itemView.findViewById(R.id.tvStudentID);
            _tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
            _tgbtnStatus = (ToggleButton) itemView.findViewById(R.id.tgbtnStatus);
            _tgbtnStatus.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.tgbtnStatus:
                    int pos = (int) _tgbtnStatus.getTag();
                    Log.v(config.AppTag, "ON Click Item Tag : " + pos);
                    AttendanceStatus status = slotAttendance.getAttendances().get(pos).getStatus();
                    if (_tgbtnStatus.isChecked()) {
                        _tgbtnStatus.setTextColor(Color.rgb(100, 221, 23));
                        status.setId(AttendanceConfig.PRESENT_ATTENDANCE);
                        status.setName(AttendanceConfig.PRESENT_ATTENDANCE_NAME);
                    } else {
                        _tgbtnStatus.setTextColor(Color.RED);
                        status.setId(AttendanceConfig.ABSENT_ATTENDANCE);
                        status.setName(AttendanceConfig.ABSENT_ATTENDANCE_NAME);
                    }
                    break;
            }
        }
    }
}
