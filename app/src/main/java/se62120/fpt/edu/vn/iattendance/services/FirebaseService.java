package se62120.fpt.edu.vn.iattendance.services;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import se62120.fpt.edu.vn.iattendance.models.fcm.FirebaseCloudMessage;

/**
 * Created by MrDat on 26/03/2018.
 */

public interface FirebaseService {
    @POST("send")
    Call<ResponseBody> send(@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @POST("send")
    Call<ResponseBody> send2(@Header("Authorization") String token, @Body RequestBody body);
}
