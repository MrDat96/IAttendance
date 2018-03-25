package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import se62120.fpt.edu.vn.iattendance.configures.AttendanceConfig;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedScanFaceAttendancesListener;
import se62120.fpt.edu.vn.iattendance.models.Result;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.services.TakeAttendanceService;

/**
 * Created by MrDat on 18/03/2018.
 */

public class ScanFacesAttendanceInteractor implements Callback<ResponseBody> {

    IOnFinishedScanFaceAttendancesListener listener;

    public ScanFacesAttendanceInteractor(IOnFinishedScanFaceAttendancesListener listener) {
        this.listener = listener;
    }

    public void scanFace(String token, String timeTableId, String[] filePaths) {
        Log.v(config.AppTag, "On Retrofit scan face...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TakeAttendanceService takeAttendanceService = retrofit.create(TakeAttendanceService.class);
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//
//        builder.addFormDataPart("time_table_id", timeTableId);
//
//        for(int i = 0; i < filePaths.length; i++) {
//            if (filePaths[i] != null) {
//                Log.v(config.AppTag, "Add path file: " + filePaths[i] + " to builer Multipart");
//                File file = new File(filePaths[i]);
//                builder.addFormDataPart("ImagesScanning", file.getName()
//                        , RequestBody.create(MediaType.parse("multipart/form-data"), file));
//            }
//        }
//        MultipartBody requestBody = builder.build();
//        Call<ResponseBody> call = takeAttendanceService.scanFaceByImagesUpload(token, requestBody);
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < filePaths.length; i++)
            if (filePaths[i] != null) {
                File file = new File(filePaths[i]);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                parts.add(part);
            }
        RequestBody timeTableIdPart = RequestBody.create(MultipartBody.FORM, timeTableId);
        Call<ResponseBody> call = takeAttendanceService.scanFaceByDynamicImagesAttendace(token, timeTableIdPart, parts);
        call.enqueue(this);
        Log.v(config.AppTag, "Calling back...");
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                Log.v(config.AppTag, "On scan faces attendances success!");
            } else {
                Log.v(config.AppTag, "Empty data");
            }
            listener.onFinishedScanFaceSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFinishedScanFaceFail();
            Log.v(config.AppTag, "On scan faces attendances fail!" + e.getStackTrace());
        }
        Log.v(config.AppTag, "Scan Face Fetched data :" + jsonStr + "End");
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.v(config.AppTag, "On scan faces attendances fail service!");
        t.printStackTrace();
        listener.onFinishedScanFaceFail();
    }
}
