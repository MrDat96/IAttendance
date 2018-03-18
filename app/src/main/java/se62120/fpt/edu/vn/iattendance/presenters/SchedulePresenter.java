package se62120.fpt.edu.vn.iattendance.presenters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IScheduleView;
import se62120.fpt.edu.vn.iattendance.models.Campus;
import se62120.fpt.edu.vn.iattendance.models.Course;
import se62120.fpt.edu.vn.iattendance.models.Room;
import se62120.fpt.edu.vn.iattendance.models.Slot;
import se62120.fpt.edu.vn.iattendance.models.StudentGroup;
import se62120.fpt.edu.vn.iattendance.models.TakeAttendanceStatus;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.services.ScheduleService;

/**
 * Created by MrDat on 17/03/2018.
 */

public class SchedulePresenter implements Callback<ResponseBody> {

    IScheduleView view;

    public SchedulePresenter(IScheduleView view) {
        this.view = view;
    }

    public Retrofit initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.BaseURL)
                .build();
        return retrofit;
    }

    public void fetchSchedule(String token, String date) {
        Retrofit retrofit = initRetrofit();
        ScheduleService scheduleService = retrofit.create(ScheduleService.class);
        Call<ResponseBody> call = scheduleService.getSchedule(token, date);
        call.enqueue(this);
    }

    public ArrayList<TimeTable> fetchSchedule2Test(String token, String date) {
        ArrayList<TimeTable> listSchedule = new ArrayList<>();
        String jsonStr = "[\n" +
                "    {\n" +
                "        \"time_table_id\": 1,\n" +
                "        \"date\": \"2018-03-17T11:59:15.367\",\n" +
                "        \"slot\": {\n" +
                "            \"id\": 1,\n" +
                "            \"start_time\": \"07:30:00\",\n" +
                "            \"end_time\": \"09:00:00\"\n" +
                "        },\n" +
                "        \"teacher_id\": \"AnhBNSE1101\",\n" +
                "        \"student_group\": {\n" +
                "            \"id\": \"IS1101\",\n" +
                "            \"name\": \"K10 - IS\"\n" +
                "        },\n" +
                "        \"room\": {\n" +
                "            \"id\": \"P201\",\n" +
                "            \"name\": \"201 - Beta\"\n" +
                "        },\n" +
                "        \"campus\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"HL-Hoa Lac\"\n" +
                "        },\n" +
                "        \"course\": {\n" +
                "            \"id\": \"PRM391\",\n" +
                "            \"name\": \"Programing with mobile\"\n" +
                "        },\n" +
                "        \"status_time_table\": {\n" +
                "            \"id\": \"1\",\n" +
                "            \"name\": \"Booked\"\n" +
                "        },\n" +
                "        \"status_take_attendance\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Waiting\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"time_table_id\": 2,\n" +
                "        \"date\": \"2018-03-17T11:59:15.367\",\n" +
                "        \"slot\": {\n" +
                "            \"id\": 2,\n" +
                "            \"start_time\": \"09:10:00\",\n" +
                "            \"end_time\": \"10:40:00\"\n" +
                "        },\n" +
                "        \"teacher_id\": \"AnhBNSE1101\",\n" +
                "        \"student_group\": {\n" +
                "            \"id\": \"IS1101\",\n" +
                "            \"name\": \"K10 - IS\"\n" +
                "        },\n" +
                "        \"room\": {\n" +
                "            \"id\": \"P201\",\n" +
                "            \"name\": \"201 - Beta\"\n" +
                "        },\n" +
                "        \"campus\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"HL-Hoa Lac\"\n" +
                "        },\n" +
                "        \"course\": {\n" +
                "            \"id\": \"PRM391\",\n" +
                "            \"name\": \"Programing with mobile\"\n" +
                "        },\n" +
                "        \"status_time_table\": {\n" +
                "            \"id\": \"1\",\n" +
                "            \"name\": \"Booked\"\n" +
                "        },\n" +
                "        \"status_take_attendance\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Waiting\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"time_table_id\": 3,\n" +
                "        \"date\": \"2018-03-17T11:59:15.367\",\n" +
                "        \"slot\": {\n" +
                "            \"id\": 3,\n" +
                "            \"start_time\": \"10:50:00\",\n" +
                "            \"end_time\": \"12:20:00\"\n" +
                "        },\n" +
                "        \"teacher_id\": \"AnhBNSE1101\",\n" +
                "        \"student_group\": {\n" +
                "            \"id\": \"IS1101\",\n" +
                "            \"name\": \"K10 - IS\"\n" +
                "        },\n" +
                "        \"room\": {\n" +
                "            \"id\": \"P201\",\n" +
                "            \"name\": \"201 - Beta\"\n" +
                "        },\n" +
                "        \"campus\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"HL-Hoa Lac\"\n" +
                "        },\n" +
                "        \"course\": {\n" +
                "            \"id\": \"PRM391\",\n" +
                "            \"name\": \"Programing with mobile\"\n" +
                "        },\n" +
                "        \"status_time_table\": {\n" +
                "            \"id\": \"1\",\n" +
                "            \"name\": \"Booked\"\n" +
                "        },\n" +
                "        \"status_take_attendance\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Waiting\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"time_table_id\": 4,\n" +
                "        \"date\": \"2018-03-17T11:59:15.367\",\n" +
                "        \"slot\": {\n" +
                "            \"id\": 4,\n" +
                "            \"start_time\": \"12:50:00\",\n" +
                "            \"end_time\": \"14:20:00\"\n" +
                "        },\n" +
                "        \"teacher_id\": \"AnhBNSE1101\",\n" +
                "        \"student_group\": {\n" +
                "            \"id\": \"IS1102\",\n" +
                "            \"name\": \"K10 - IS\"\n" +
                "        },\n" +
                "        \"room\": {\n" +
                "            \"id\": \"P202\",\n" +
                "            \"name\": \"202 - Beta\"\n" +
                "        },\n" +
                "        \"campus\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"HL-Hoa Lac\"\n" +
                "        },\n" +
                "        \"course\": {\n" +
                "            \"id\": \"SWD391\",\n" +
                "            \"name\": \"Software Architecture and Design\"\n" +
                "        },\n" +
                "        \"status_time_table\": {\n" +
                "            \"id\": \"1\",\n" +
                "            \"name\": \"Booked\"\n" +
                "        },\n" +
                "        \"status_take_attendance\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Waiting\"\n" +
                "        }\n" +
                "    }\n" +
                "]";
        listSchedule = convertToListTimeTable(jsonStr);
        return listSchedule;
    }

    private ArrayList<TimeTable> convertToListTimeTable(String jsonStr) {
        ArrayList<TimeTable> list = new ArrayList<>();
        try {
            JSONArray arrJSON = new JSONArray(jsonStr);
            for(int i = 0; i < arrJSON.length(); i++) {
                JSONObject timeTableObj = arrJSON.getJSONObject(i);

                int time_table_id = timeTableObj.getInt("time_table_id");

                String strDate = timeTableObj.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(strDate);

                JSONObject slotObj = timeTableObj.getJSONObject("slot");
                int slotID = slotObj.getInt("id");
                String start_time = slotObj.getString("start_time");
                String end_time = slotObj.getString("end_time");
                Slot slot = new Slot(slotID, start_time, end_time);

                JSONObject student_group_obj = timeTableObj.getJSONObject("student_group");
                String studentGroupId = student_group_obj.getString("id");
                String studentGroupName = student_group_obj.getString("name");
                StudentGroup studentGroup = new StudentGroup(studentGroupId, studentGroupName);

                JSONObject roomObj = timeTableObj.getJSONObject("room");
                String roomId = roomObj.getString("id");
                String roomName = roomObj.getString("name");
                Room room = new Room(roomId, roomName);

                JSONObject campusObj = timeTableObj.getJSONObject("campus");
                String campusId = campusObj.getString("id");
                String campusName = campusObj.getString("name");
                Campus campus = new Campus(campusId, campusName);

                JSONObject courseObj = timeTableObj.getJSONObject("course");
                String courseId = courseObj.getString("id");
                String courseName = courseObj.getString("name");
                Course course = new Course(courseId, courseName);

                JSONObject TakeAttendanceStatusObj = timeTableObj.getJSONObject("status_take_attendance");
                int takeId = TakeAttendanceStatusObj.getInt("id");
                String takeName = TakeAttendanceStatusObj.getString("name");
                TakeAttendanceStatus take = new TakeAttendanceStatus(takeId, takeName);

                TimeTable timeTable = new TimeTable();
                timeTable.setId(time_table_id);
                timeTable.setDate(date);
                timeTable.setSlot(slot);
                timeTable.setStudentGroup(studentGroup);
                timeTable.setRoom(room);
                timeTable.setCampus(campus);
                timeTable.setCourse(course);
                timeTable.setTakeAttendanceStatus(take);

                list.add(timeTable);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        String jsonStr = null;
        try {
            if (response.body() != null) {
                jsonStr = response.body().string();
                view.onFetchScheduleSuccess(convertToListTimeTable(jsonStr));
            } else {
                Log.v(config.AppTag, "Empty data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.onFetchScheduleFail();
        }
        Log.v(config.AppTag, "Fetched data :" + jsonStr);
    }


    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        view.onFetchScheduleFail();
        Log.v(config.AppTag, "Schedule fetch data failed!");
    }
}
