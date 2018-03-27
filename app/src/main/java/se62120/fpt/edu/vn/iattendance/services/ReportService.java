package se62120.fpt.edu.vn.iattendance.services;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MrDat on 27/03/2018.
 */

public interface ReportService {
    @POST("api/message/report")
    Call<ResponseBody> insertReport(@Header("Authorization") String token, @Query("timeTableId") String timeTableId, @Body RequestBody body);

    @GET("api/message")
    Call<ResponseBody> getReportsHistory(@Header("Authorization") String token);

    @POST("api/message/report")
    Call<ResponseBody> updateStatusReport(@Header("Authorization") String token, @Query("messageId") String reportId,
                                    @Query("statusId") String statusId);

}
