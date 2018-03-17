package se62120.fpt.edu.vn.iattendance.services;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import se62120.fpt.edu.vn.iattendance.models.User;

/**
 * Created by MrDat on 16/03/2018.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("api/Login")
    Call<ResponseBody> credential(@Field("username") String username, @Field("password") String password,
                                  @Field("grant_type") String grant_type);
}
