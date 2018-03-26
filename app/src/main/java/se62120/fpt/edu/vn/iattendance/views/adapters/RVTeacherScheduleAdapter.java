package se62120.fpt.edu.vn.iattendance.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.views.activities.StudentReportActivity;
import se62120.fpt.edu.vn.iattendance.views.activities.TakeAttendanceActivity;

/**
 * Created by MrDat on 13/03/2018.
 */

public class RVTeacherScheduleAdapter extends RecyclerView.Adapter<RVTeacherScheduleAdapter.RVViewHolder> {

    ArrayList<TimeTable> list = new ArrayList<>();
    int role = -1;

    public RVTeacherScheduleAdapter(ArrayList<TimeTable> list, int role) {
        this.list = list;
        this.role = role;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("TEST", "I'M HERE ON CREATE");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item_layout, parent, false);
        return new RVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.v("TEST", "I'M HERE ON BIND");
        //HashMap<String, String> hashMap = hashMaps.get(position);
        TimeTable timeTable = list.get(position);
        //holder._tvTitleSchedule.setText("SLOT X FROM Y TO Z " + hashMap.get("slot"));
        String title = "SLOT " + timeTable.getSlot().getId() + ":  "
                        + timeTable.getSlot().getStartTime().substring(0,5) + " -> " + timeTable.getSlot().getEndTime().substring(0,5);
        holder._tvTitleSchedule.setText(title);
        holder._tvSubTitleSchedule.setText(timeTable.getCourse().getName());
        holder._tvRoomSchedule.setText(timeTable.getRoom().getId());
        holder._tvClassSchedule.setText(timeTable.getStudentGroup().getId());
        holder._tvCampusSchedule.setText(timeTable.getCampus().getName());

        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public CardView cardView;
        private final Context context;
        TextView _tvTitleSchedule;
        TextView _tvSubTitleSchedule;
        TextView _tvRoomSchedule;
        TextView _tvClassSchedule;
        TextView _tvCampusSchedule;

        public RVViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            _tvTitleSchedule = (TextView) itemView.findViewById(R.id.tvTitleSchedule);
            _tvSubTitleSchedule = (TextView) itemView.findViewById(R.id.tvSubTitleSchedule);
            _tvRoomSchedule = (TextView) itemView.findViewById(R.id.tvRoomSchedule);
            _tvClassSchedule = (TextView) itemView.findViewById(R.id.tvClassSchedule);
            _tvCampusSchedule = (TextView) itemView.findViewById(R.id.tvCampusSchedule);
            itemView.setOnCreateContextMenuListener(this);

            cardView = (CardView) itemView.findViewById(R.id.cvSchedule);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle(_tvTitleSchedule.getText() + " \r\n " + _tvSubTitleSchedule.getText());
            if (role == config.ROLE_TEACHER) {
                MenuItem checkAttendance = contextMenu.add(Menu.NONE, 1, 1, "Check Attendance");
                checkAttendance.setOnMenuItemClickListener(onEditMenu);
            }
            MenuItem viewItem = contextMenu.add(Menu.NONE, 2, 2, "View");
            viewItem.setOnMenuItemClickListener(onEditMenu);

            Log.v(config.AppTag, "Role on Schedule :" + role);
            if (role == config.ROLE_STUDENT) {
                MenuItem reportItem = contextMenu.add(Menu.NONE, 3, 3, "Report to teacher");
                reportItem.setOnMenuItemClickListener(onEditMenu);
            }
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = null;
                int position;
                TimeTable timeTable;
                switch (menuItem.getItemId()) {
                    case 1:
                        Log.v("TEST", "Check Attendance");
                        intent = new Intent(context, TakeAttendanceActivity.class);
//                        intent.putExtra("date", "14/03/2018");
//                        intent.putExtra("class", "IS1101");
//                        intent.putExtra("slot", 1);
                        Log.v(config.AppTag, cardView.getTag() + "");
                        position = (int) cardView.getTag();
                        timeTable = list.get(position);
                        intent.putExtra("TimeTable", timeTable);
                        break;
                    case 2:
                        Log.v("TEST", "View");
                        break;
                    case 3:
                        Log.v(config.AppTag, "Report");
                        intent = new Intent(context, StudentReportActivity.class);
                        position = (int) cardView.getTag();
                        timeTable = list.get(position);
                        intent.putExtra("TimeTable", timeTable);
                        break;
                }
                context.startActivity(intent);
                return true;
            }
        };
    }

}
