package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnReportUpdateStatusListener;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.services.ReportService;

/**
 * Created by MrDat on 28/03/2018.
 */

public class ReportUpdateStatusInteractor implements Callback<ResponseBody> {

    IOnReportUpdateStatusListener listener;

    public ReportUpdateStatusInteractor(IOnReportUpdateStatusListener listener) {
        this.listener = listener;
    }

    public void updateReportStatus(String token, int messageId, int statusId) {
        Log.v(config.AppTag, "On get reportst with token :" + token);

        Retrofit retrofit = RetrofitSupport.initRetrofit();
        ReportService reportService = retrofit.create(ReportService.class);
        Call<ResponseBody> call = reportService.updateStatusReport(token, messageId +"", statusId + "");
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                listener.onReportUpdateStatusSuccessful();
                Log.v(config.AppTag, "On fetched attendances success!");
            } else {
                Log.v(config.AppTag, "Empty data");
                listener.onReportUpdateStatusFail();
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onReportUpdateStatusFail();
        }
        Log.v(config.AppTag, "Check get all reports Fetched data :" + jsonStr);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.v(config.AppTag, "Get all history reports fail");
        listener.onReportUpdateStatusFail();
    }
}
