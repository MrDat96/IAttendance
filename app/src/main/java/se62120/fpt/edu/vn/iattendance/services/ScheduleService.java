package se62120.fpt.edu.vn.iattendance.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MrDat on 17/03/2018.
 */

public interface ScheduleService {
    @GET("api/timetable")
    Call<ResponseBody> getSchedule(@Header("Authorization") String token, @Query("date") String date);
}
