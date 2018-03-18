package se62120.fpt.edu.vn.iattendance.models;

import retrofit2.Retrofit;
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
}
