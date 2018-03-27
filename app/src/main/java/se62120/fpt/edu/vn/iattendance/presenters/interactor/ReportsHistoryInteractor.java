package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnGetReportsHistoryListener;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.Slot;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;
import se62120.fpt.edu.vn.iattendance.models.reports.ReportStatus;
import se62120.fpt.edu.vn.iattendance.services.ReportService;

/**
 * Created by MrDat on 28/03/2018.
 */

public class ReportsHistoryInteractor implements Callback<ResponseBody> {

    IOnGetReportsHistoryListener listener;

    public ReportsHistoryInteractor(IOnGetReportsHistoryListener listener) {
        this.listener = listener;
    }

    public void getReportsHistory(String token) {

        Log.v(config.AppTag, "On get reportst with token :" + token);

        Retrofit retrofit = RetrofitSupport.initRetrofit();
        ReportService reportService = retrofit.create(ReportService.class);
        Call<ResponseBody> call = reportService.getReportsHistory(token);
        call.enqueue(this);
    }

    public ArrayList<Report> convertJSONToList(String data) {
        ArrayList<Report> reports = new ArrayList<>();

        try {
            JSONArray arrObj = new JSONArray(data);
            for(int i = 0; i < arrObj.length(); i++) {
                JSONObject obj = arrObj.getJSONObject(i);

                int message_id = obj.getInt("message_id");

                String sender = obj.getString("sender");
                String receiver = obj.getString("receiver");
                String topic = obj.getString("topic");
                String description = obj.getString("description");

                String strDate = obj.getString("date_timetable");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(strDate);


                JSONObject slotObj = obj.getJSONObject("slot");
                int id = slotObj.getInt("id");
                String start_time = slotObj.getString("start_time");
                String end_time = slotObj.getString("end_time");
                Slot slot = new Slot(id, start_time, end_time);

                JSONObject reportStatusObj = obj.getJSONObject("status_message");
                int rsId = reportStatusObj.getInt("id");
                String rsName = reportStatusObj.getString("name");
                ReportStatus reportStatus = new ReportStatus(rsId, rsName);

                Report report = new Report();
                report.setId(message_id);
                report.setSender(sender);
                report.setReceiver(receiver);
                report.setTopic(topic);
                report.setDescription(description);
                report.setDate(date);
                report.setSlot(slot);
                report.setReportStatus(reportStatus);

                reports.add(report);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reports;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                ArrayList<Report> reports = convertJSONToList(jsonStr);
                listener.onScheduleSuccessListener(reports);
                Log.v(config.AppTag, "On fetched attendances success! size: " + reports.size());
            } else {
                Log.v(config.AppTag, "Empty data");
                listener.onFetchScheduleFailListner();
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFetchScheduleFailListner();
        }
        Log.v(config.AppTag, "Check get all reports Fetched data :" + jsonStr);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.v(config.AppTag, "Get all history reports fail");
        listener.onFetchScheduleFailListner();
    }
}
