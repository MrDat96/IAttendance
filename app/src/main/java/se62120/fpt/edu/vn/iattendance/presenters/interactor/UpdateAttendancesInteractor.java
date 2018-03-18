package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MrDat on 18/03/2018.
 */

public class UpdateAttendancesInteractor implements Callback<ResponseBody> {


    public void updateAttendance(String token, String data) {

    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
