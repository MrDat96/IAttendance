package se62120.fpt.edu.vn.iattendance.services;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import se62120.fpt.edu.vn.iattendance.models.Result;

/**
 * Created by MrDat on 18/03/2018.
 */

public interface TakeAttendanceService {
    @GET("api/Attendance")
    Call<ResponseBody> getAttendance(@Header("Authorization") String token, @Query("TimeTableId") String timeTableID);

    @POST("api/attendance/update/manual/all")
    Call<ResponseBody> updateAttendance(@Header("Authorization") String token, @Query("timeTableId") String timeTableID, @Body RequestBody body);

    @POST("api/attendance/update/identity")
    Call<ResponseBody> scanFaceByImagesUpload(@Header("Authorization") String token,@Query("timeTableId") String timeTableId,@Body RequestBody file);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/attendance/update/identity")
    Call<ResponseBody> scanFaceByDynamicImagesAttendace2(@Header("Authorization") String token, @Part("timeTableId") RequestBody timeTableID,
                                                      @Part List<MultipartBody.Part> files);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/attendance/update/identity")
    Call<ResponseBody> scanFaceByDynamicImagesAttendace3(@Header("Authorization") String token, @Query("timeTableId") String timeTableID,
                                                        @Part List<MultipartBody.Part> files);
    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/attendance/update/identity")
    Call<ResponseBody> scanFaceByDynamicImagesAttendaceOneFile(@Header("Authorization") String token, @Query("timeTableId") String timeTableID,
                                                               @Part("image") RequestBody image);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/attendance/update/identity?timeTableId=1")
    Call<ResponseBody> scanFaceByDynamicImagesAttendace4(@Header("Authorization") String token, @Part List<MultipartBody.Part> files);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/attendance/update/identity")
    Call<ResponseBody> scanFaceByDynamicImagesAttendace5(@Header("Authorization") String token, @Query("timeTableId") String timeTableId,@Part MultipartBody.Part file);

    @Multipart
    @POST("api/Attendance/Update/StatusAttendance/StudentList/FaceScan")
    Call<List<Result>> scanFacesAttendace(@Header("Authorization") String token, @Part("time_table_id") RequestBody timeTableID,
                                          @Part List<MultipartBody.Part> files);
}
