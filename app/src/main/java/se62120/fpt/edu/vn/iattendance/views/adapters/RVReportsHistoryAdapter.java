package se62120.fpt.edu.vn.iattendance.views.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.AttendanceConfig;
import se62120.fpt.edu.vn.iattendance.interfaces.IReportsView;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;

/**
 * Created by MrDat on 26/03/2018.
 */

public class RVReportsHistoryAdapter extends RecyclerView.Adapter<RVReportsHistoryAdapter.RVViewHolder> {

    IReportsView reportView;

    ArrayList<Report> reports = new ArrayList<>();
    int role = -1;

    public RVReportsHistoryAdapter(ArrayList<Report> reports, int role, IReportsView reportView) {
        this.reports = reports;
        this.role = role;
        this.reportView = reportView;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_layout, parent, false);
        return new RVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
//        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder._ivAvatar.getLayoutParams();
//        params.width = 70;
//        params.height = 70;
//        holder._ivAvatar.setLayoutParams(params);

        Report report = reports.get(position);
        holder._tvTopic.setText(report.getTopic());
        holder._tvDescription.setText(report.getDescription());
        holder._tvDescription.setTextColor(Color.BLUE);
        holder._tvDate.setText(report.getDate().toString());
        holder._tvStatus.setText(report.getReportStatus().getName());

        if (role == AttendanceConfig.ROLE_STUDENT) {
            holder._btnApprove.setVisibility(View.GONE);
            holder._btnReject.setVisibility(View.GONE);
            holder._tvName.setText(report.getReceiver());
        } else {
            holder._tvName.setText(report.getSender());
        }

        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (reports == null) return 0;
        return reports.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cardView;
        TextView _tvName;
        TextView _tvStudentID;
        TextView _tvTopic;
        TextView _tvDescription;
        TextView _tvDate;
        TextView _tvStatus;
        Button _btnApprove;
        Button _btnReject;

        public RVViewHolder(View itemView) {
            super(itemView);
            _tvName = (TextView) itemView.findViewById(R.id.tvName);
            _tvStudentID = (TextView) itemView.findViewById(R.id.tvStudentID);
            _tvTopic = (TextView) itemView.findViewById(R.id.tvTopic);
            _tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            _tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            _tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            _btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            _btnReject = (Button) itemView.findViewById(R.id.btnReject);

            cardView = (CardView) itemView.findViewById(R.id.cvReport);

            _btnReject.setOnClickListener(this);
            _btnApprove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btnApprove:
                    reportView.onApproveReport((int)cardView.getTag());
                    break;
                case R.id.btnReject:
                    reportView.onRejectReport((int)cardView.getTag());
                    break;
            }
        }
    }

}
