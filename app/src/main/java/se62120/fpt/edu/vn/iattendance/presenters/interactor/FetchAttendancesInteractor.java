package se62120.fpt.edu.vn.iattendance.presenters.interactor;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedFetchAttendancesListener;
import se62120.fpt.edu.vn.iattendance.models.Attendance;
import se62120.fpt.edu.vn.iattendance.models.AttendanceStatus;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.Student;
import se62120.fpt.edu.vn.iattendance.services.TakeAttendanceService;

/**
 * Created by MrDat on 18/03/2018.
 */

public class FetchAttendancesInteractor implements Callback<ResponseBody> {
    IOnFinishedFetchAttendancesListener listener;

    public FetchAttendancesInteractor(IOnFinishedFetchAttendancesListener listener) {
        this.listener = listener;
    }

    public void fecthAttendance(String token, String timeTableID) {
        Retrofit retrofit = RetrofitSupport.initRetrofit();
        TakeAttendanceService takeAttendanceService = retrofit.create(TakeAttendanceService.class);
        Call<ResponseBody> call = takeAttendanceService.getAttendance(token, timeTableID);
        call.enqueue(this);
    }

    public ArrayList<Attendance> convertFromJson(String jsonStr) {

        ArrayList<Attendance> attendances = new ArrayList<>();
        try {
            JSONArray attendancesJSON = new JSONArray(jsonStr);
            for(int i = 0; i < attendancesJSON.length(); i++) {
                JSONObject attendanceJSON = attendancesJSON.getJSONObject(i);

                JSONObject studentObjJSON = attendanceJSON.getJSONObject("student");
                String id = studentObjJSON.getString("id");
                String avatarSrc = studentObjJSON.getString("image_path");
                JSONObject detailObjJSON = studentObjJSON.getJSONObject("student_detail");
                String name = detailObjJSON.getString("name");
                String email = detailObjJSON.getString("Email");
                Student student = new Student(id, name, email, avatarSrc);

                JSONObject attendanceStatusObjJSON = attendanceJSON.getJSONObject("status_attendance");
                int attendanceStatusID = attendanceStatusObjJSON.getInt("id");
                String attendanceStatusName = attendanceStatusObjJSON.getString("name");
                AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceStatusID, attendanceStatusName);

                Attendance attendance = new Attendance(student, attendanceStatus);
                attendances.add(attendance);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        attendances.add(new Attendance( new Student("SE62120", "Ngo Thuc Dat", "datntse62120@fpt.edu.vn",
//                "https://pixabay.com/vi/avatar-nam-c%E1%BA%ADu-b%C3%A9-nh%C3%A2n-v%E1%BA%ADt-1606916/")
//                , new AttendanceStatus(0, "Absent")));
//        attendances.add(new Attendance( new Student("SE04220", "Can Xuan Quang", "quangcxse04220@fpt.edu.vn",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzhzsaQ96OMTAYic8ylTyw63zxDlb6yS6EP-eexi1T4S31x3N8")
//                , new AttendanceStatus(1, "Present")));
//        attendances.add(new Attendance( new Student("SE04002", "Nguyen Chi Thanh", "thanhncse04002@fpt.edu.vn",
//                "https://pixabay.com/vi/avatar-nam-c%E1%BA%ADu-b%C3%A9-nh%C3%A2n-v%E1%BA%ADt-1606916/")
//                , new AttendanceStatus(1, "Present")));
//        attendances.add(new Attendance( new Student("SE62120", "Ngo Thuc Dat", "datntse62120@fpt.edu.vn",
//                "https://cdn2.iconfinder.com/data/icons/avatar-2/512/Fred_man-512.png")
//                , new AttendanceStatus(0, "Absent")));
//        attendances.add(new Attendance( new Student("SE62120", "Ngo Thuc Dat", "datntse62120@fpt.edu.vn",
//                "https://www.vexels.com/media/users//3/145922/raw/eb6591b54b2b6462b4c22ec1fc4c36ea.jpg")
//                , new AttendanceStatus(0, "Absent")));
//        slotAttendance.setAttendances(attendances);
        return attendances;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                //view.onFetchAttendanceSuccess(convertFromJson(jsonStr));
                listener.onFetchAttendanceSuccess(convertFromJson(jsonStr));
                Log.v(config.AppTag, "On fetched attendances success!");
            } else {
                //Testing only
                //view.onFetchAttendanceSuccess(convertFromJson(jsonStr));
                //listener.onFetchAttendanceSuccess(convertFromJson(jsonStr));
                Log.v(config.AppTag, "Empty data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFetchAttendanceFail();
        }
        Log.v(config.AppTag, "Check Attendance Fetched data :" + jsonStr);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        listener.onFetchAttendanceFail();
        Log.v(config.AppTag, "Check Attendance fetch data failed!");
    }
}
