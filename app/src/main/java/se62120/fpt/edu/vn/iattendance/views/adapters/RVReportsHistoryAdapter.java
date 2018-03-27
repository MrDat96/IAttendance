package se62120.fpt.edu.vn.iattendance.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;

/**
 * Created by MrDat on 26/03/2018.
 */

public class RVReportsHistoryAdapter extends RecyclerView.Adapter<RVReportsHistoryAdapter.RVViewHolder> {

    ArrayList<Report> reports = new ArrayList<>();
    int role = -1;

    public RVReportsHistoryAdapter(ArrayList<Report> reports, int role) {
        this.reports = reports;
        this.role = role;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_layout, parent, false);
        return new RVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder._ivAvatar.getLayoutParams();
        params.width = 70;
        params.height = 70;
        holder._ivAvatar.setLayoutParams(params);

        Report report = reports.get(position);

        holder._tvTopic.setText(report.getTopic());
        holder._tvStudentName.setText(report.getStudent().getName());
        holder._tvStudentID.setText(report.getStudent().getId());
        holder._tvDescription.setText(report.getDescription());
    }

    @Override
    public int getItemCount() {
        if (reports == null) return 0;
        return reports.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder  {

        ImageView _ivAvatar;
        TextView _tvStudentName;
        TextView _tvStudentID;
        TextView _tvTopic;
        TextView _tvDescription;
        Button _btnApprove;
        Button _btnReject;

        public RVViewHolder(View itemView) {
            super(itemView);
            _ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            _tvStudentName = (TextView) itemView.findViewById(R.id.tvStudentName);
            _tvStudentID = (TextView) itemView.findViewById(R.id.tvStudentID);
            _tvTopic = (TextView) itemView.findViewById(R.id.tvTopic);
            _tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            _btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            _btnReject = (Button) itemView.findViewById(R.id.btnReject);
        }
    }

}
