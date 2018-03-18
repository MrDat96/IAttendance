package se62120.fpt.edu.vn.iattendance.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface TakeAttendanceService {
    @GET("api/Attendance")
    Call<ResponseBody> getAttendance(@Header("Authorization") String token, @Query("Id") String timeTableID);

    @POST("api/UpdateAttendance")
    Call<ResponseBody> updateAttendance(@Header("Authorization") String token, @Body String data);
}
