package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedStudentSendReportNoticationListener;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.fcm.Data;
import se62120.fpt.edu.vn.iattendance.models.fcm.FirebaseCloudMessage;
import se62120.fpt.edu.vn.iattendance.services.FirebaseService;

/**
 * Created by MrDat on 25/03/2018.
 */

public class StudentReportSendNotificationInteractor {

    IOnFinishedStudentSendReportNoticationListener listener;

    public StudentReportSendNotificationInteractor(IOnFinishedStudentSendReportNoticationListener listener) {
        this.listener = listener;
    }

    public void sendNotification(String from, String to, final String topic, final String description) {

        // get token of teacher
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //reference.child("users").child(to).getKey();
        final String to2 = to;
        reference.child("users").child(to).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(config.AppTag, "Get token of teacher notification : (" + to2 + ")" + value);
                sendMessageToTeacher(topic,description, value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFinishedStudentSentReportNotificationFail();
            }
        });
    }

    private void sendMessageToTeacher(String title, String message, String toToken) {
        Log.d(config.AppTag, "Send to message to teacher!");
        Retrofit retrofit = RetrofitSupport.initRetrofit(config.BaseNotiURL);
        FirebaseService firebaseService = retrofit.create(FirebaseService.class);
        // attach headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "key=AIzaSyAJpNypuhyY45jGf12uHT9_o9ghfRqCERM");

        Data data = new Data(title, message);

        FirebaseCloudMessage firebaseCloudMessage = new FirebaseCloudMessage();
        firebaseCloudMessage.setData(data);
        firebaseCloudMessage.setTo(toToken);
        String dataJson = firebaseCloudMessage.toJSONString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), dataJson);

        Log.v(config.AppTag, "DATA Send: " + dataJson);

        //Call<ResponseBody> call = firebaseService.send(headers, body);
        Call<ResponseBody> call = firebaseService.send2("key=AIzaSyAJpNypuhyY45jGf12uHT9_o9ghfRqCERM", body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(config.AppTag, "Send to message to teacher success!");
                try {
                    if (response.body() != null) {
                        String body = response.body().string();
                        Log.v(config.AppTag, "Reponse: " + body);
                    } else {
                        Log.v(config.AppTag, "Null Reponse body");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                };
                listener.onFinishedStudentSendReportNotificationSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(config.AppTag, "Send to message to teacher fail!");
                listener.onFinishedStudentSentReportNotificationFail();
            }
        });
    }

}
