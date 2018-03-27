package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportListenser;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.reports.Report;
import se62120.fpt.edu.vn.iattendance.services.ReportService;

/**
 * Created by MrDat on 27/03/2018.
 */

public class SendReportInteractor implements Callback<ResponseBody>{
    IOnFinishedStudentSendReportListenser listener;

    public SendReportInteractor(IOnFinishedStudentSendReportListenser listener) {
        this.listener = listener;
    }

    public void sendReportToTeacher(String token, String timeTableId, String data ) {
        Log.v(config.AppTag, "Send data: " + data + "to TimetableID" + timeTableId);

        Retrofit retrofit = RetrofitSupport.initRetrofit();
        ReportService reportService = retrofit.create(ReportService.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);
        Log.v(config.AppTag, "Call service token:" + token);
        Log.v(config.AppTag, "TimetableId: "+ timeTableId + " , Data: " + data);
        Call<ResponseBody> call = reportService.insertReport(token, timeTableId, body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                listener.onFinishedStudentSendReportSuccess();
                Log.v(config.AppTag, "On fetched attendances success!");
            } else {
                Log.v(config.AppTag, "Empty data");
                listener.onFinishedStudentSentReportFail();
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFinishedStudentSentReportFail();
        }
        Log.v(config.AppTag, "Check Send Message Fetched data :" + jsonStr);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        listener.onFinishedStudentSentReportFail();
    }
}
