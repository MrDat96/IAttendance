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
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedUpdateAttendancesLisnter;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.services.TakeAttendanceService;

/**
 * Created by MrDat on 18/03/2018.
 */

public class UpdateAttendancesInteractor implements Callback<ResponseBody> {

    IOnFinishedUpdateAttendancesLisnter listener;

    public UpdateAttendancesInteractor(IOnFinishedUpdateAttendancesLisnter listener) {
        this.listener = listener;
    }

    public void updateAttendance(String token, String timeTableId,String data) {
        Log.v(config.AppTag, "On update of Token: " + token);
        Log.v(config.AppTag,"Time table Id: " + timeTableId + ", data:" + data);
        Retrofit retrofit = RetrofitSupport.initRetrofit();
        TakeAttendanceService takeAttendanceService = retrofit.create(TakeAttendanceService.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data);
        Call<ResponseBody> call = takeAttendanceService.updateAttendance(token, timeTableId, body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                listener.OnUpdateAttendanceSuccess(response.code(), "Success");
                Log.v(config.AppTag, "On fetched attendances success!");
            } else {
                Log.v(config.AppTag, "Empty data");
                listener.OnUpdateAttendanceFail(1, "Failed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.OnUpdateAttendanceFail(1, e.getMessage());
        }
        Log.v(config.AppTag, "Check Attendance Fetched data :" + jsonStr);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        listener.OnUpdateAttendanceFail(1, "Calling update attendances fail!");
    }
}
