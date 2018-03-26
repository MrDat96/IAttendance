package se62120.fpt.edu.vn.iattendance.models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se62120.fpt.edu.vn.iattendance.configures.config;

/**
 * Created by MrDat on 18/03/2018.
 */

public class RetrofitSupport {
    public static Retrofit initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.BaseURL)
                .build();
        return retrofit;
    }
    public static Retrofit initRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
