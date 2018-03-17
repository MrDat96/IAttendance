package se62120.fpt.edu.vn.iattendance.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.OnLoginFinishedListener;
import se62120.fpt.edu.vn.iattendance.services.LoginService;

/**
 * Created by MrDat on 16/03/2018.
 */

public class LoginInteractor implements Callback<ResponseBody> {
    private OnLoginFinishedListener listener;

    public LoginInteractor(OnLoginFinishedListener listener) {
        this.listener = listener;
    }

    public Retrofit initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.BaseURL)
                .build();
        return retrofit;
    }

    public void credential(String username, String password) {
        Retrofit retrofit = initRetrofit();
        LoginService loginService = retrofit.create(LoginService.class);
//        String bodyData = "{";
//        bodyData += "\"username\":\"" + username + "\",";
//        bodyData += "\"password\":\"" + password + "\",";
//        bodyData += "\"grant_type\":\"" + "password" + "\"}";
//        Log.v(config.AppTag, "Body data: " + bodyData);
        Call<ResponseBody> call = loginService.credential(username, password, "password");
        call.enqueue(this);
    }

    // Response success and save token and user to Share_preferences
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.v(config.AppTag, "Mr DAT Sucess");
        try {
            //Log.v(config.AppTag, response.body().string());
            String jsonStr = response.body().string();
            Log.v(config.AppTag, jsonStr);
            JSONObject reader = new JSONObject(jsonStr);
            String token = reader.getString("access_token");
            String username = reader.getString("userName");
            listener.onSuccess(username, token);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onError();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        listener.onError();
        Log.v(config.AppTag, "MR DAT fail");
    }
}
