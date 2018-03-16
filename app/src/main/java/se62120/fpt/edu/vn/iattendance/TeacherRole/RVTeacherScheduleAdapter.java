package se62120.fpt.edu.vn.iattendance.TeacherRole;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import java.util.HashMap;

import se62120.fpt.edu.vn.iattendance.R;

/**
 * Created by MrDat on 13/03/2018.
 */

public class RVTeacherScheduleAdapter extends RecyclerView.Adapter<RVTeacherScheduleAdapter.RVViewHolder> {

    ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();

    public RVTeacherScheduleAdapter(ArrayList<HashMap<String, String>> hashMaps) {
        this.hashMaps = hashMaps;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("TEST", "I'M HERE ON CREATE");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item_layout, parent, false);
        return new RVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        Log.v("TEST", "I'M HERE OB BIND");
        HashMap<String, String> hashMap = hashMaps.get(position);
        holder._tvTitleSchedule.setText("SLOT X FROM Y TO Z " + hashMap.get("slot"));
    }

    @Override
    public int getItemCount() {
        return hashMaps.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle(_tvTitleSchedule.getText() + " \r\n " + _tvSubTitleSchedule.getText());
            MenuItem FaceScan = contextMenu.add(Menu.NONE, 1, 1, "Face Scan");
            MenuItem TakeMannual = contextMenu.add(Menu.NONE, 2, 2, "Take manual");
            MenuItem Edit = contextMenu.add(Menu.NONE, 3, 3, "Edit");
            MenuItem ViewItem = contextMenu.add(Menu.NONE, 4, 4, "View");

            TakeMannual.setOnMenuItemClickListener(onEditMenu);
            FaceScan.setOnMenuItemClickListener(onEditMenu);
            Edit.setOnMenuItemClickListener(onEditMenu);
            ViewItem.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case 1:
                        Log.v("TEST", "Face scan");
                        break;
                    case 2:
                        Log.v("TEST", "Take manual");
                        intent = new Intent(context, ManualTakeAttendanceActivity.class);
                        intent.putExtra("date", "14/03/2018");
                        intent.putExtra("class", "IS1101");
                        intent.putExtra("slot", 1);
                        break;
                    case 3:
                        Log.v("TEST", "Edit");
                        break;
                    case 4:
                        Log.v("TEST", "View");
                        break;
                }
                context.startActivity(intent);
                return true;
            }
        };
    }

}
