package se62120.fpt.edu.vn.iattendance.TeacherRole;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.models.DownloadImageTask;

public class ManualTakeAttendanceActivity extends AppCompatActivity {

    String jsonString = "";

    RecyclerView _rvManualTaken;
    RVTeacherManualTakeAdapter _rvTeacherManualTakeAdapter;
    ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_take_attendance);
        getSupportActionBar().setTitle("Take Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _rvManualTaken = (RecyclerView) findViewById(R.id.rvManualTaken);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int _slot = extras.getInt("slot");
            String _class = extras.getString("class");
            String _date = extras.getString("date");
        } else {
            Toast.makeText(getApplicationContext(), "No extra", Toast.LENGTH_SHORT).show();
        }

        hashMaps = getArrHashMaps();

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        _rvManualTaken.setLayoutManager(llm);
        _rvTeacherManualTakeAdapter = new RVTeacherManualTakeAdapter(hashMaps);
        _rvManualTaken.setAdapter(_rvTeacherManualTakeAdapter);

//        ImageView imgAvatar = (ImageView)findViewById(R.id.ivAvatarManualTaken);
//        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) imgAvatar.getLayoutParams();
//        params.width = 70;
//        params.height = 70;
//        imgAvatar.setLayoutParams(params);
//        new DownloadImageTask(imgAvatar).execute("https://www.w3schools.com/howto/img_avatar.png");
    }

    private ArrayList<HashMap<String, String>> getArrHashMaps() {
        ArrayList<HashMap<String, String>> arrHashMaps = new ArrayList<>();
        HashMap<String, String> p1 = new HashMap<>();
        HashMap<String, String> p2 = new HashMap<>();
        HashMap<String, String> p3 = new HashMap<>();
        HashMap<String, String> p4 = new HashMap<>();
        HashMap<String, String> p5 = new HashMap<>();
        HashMap<String, String> p6 = new HashMap<>();
        HashMap<String, String> p7 = new HashMap<>();
        HashMap<String, String> p8 = new HashMap<>();
        HashMap<String, String> p9 = new HashMap<>();
        HashMap<String, String> p10 = new HashMap<>();
        p1.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p1.put("studentID", "SE62120");
        p1.put("studentName", "Ngo Thuc Dat");
        p1.put("status", "1");

        p2.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p2.put("studentID", "SE62120");
        p2.put("studentName", "Ngo Thuc Dat");
        p2.put("status", "1");

        p3.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p3.put("studentID", "SE62120");
        p3.put("studentName", "Ngo Thuc Dat");
        p3.put("status", "1");

        p4.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p4.put("studentID", "SE62120");
        p4.put("studentName", "Ngo Thuc Dat");
        p4.put("status", "1");

        p5.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p5.put("studentID", "SE62120");
        p5.put("studentName", "Ngo Thuc Dat");
        p5.put("status", "1");

        p6.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p6.put("studentID", "SE62120");
        p6.put("studentName", "Ngo Thuc Dat");
        p6.put("status", "1");

        p7.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p7.put("studentID", "SE62120");
        p7.put("studentName", "Le Lam Hung");
        p7.put("status", "0");

        p8.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p8.put("studentID", "SE62120");
        p8.put("studentName", "Can Xuan Quang");
        p8.put("status", "0");

        p9.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p9.put("studentID", "SE62120");
        p9.put("studentName", "Le Lam Hung");
        p9.put("status", "1");

        p10.put("src", "https://www.w3schools.com/howto/img_avatar.png");
        p10.put("studentID", "SE62312");
        p10.put("studentName", "Nguyen Thi Minh Nguyet");
        p10.put("status", "0");
        arrHashMaps.add(p1);
        arrHashMaps.add(p2);
        arrHashMaps.add(p3);
        arrHashMaps.add(p4);
        arrHashMaps.add(p5);
        arrHashMaps.add(p6);
        arrHashMaps.add(p7);
        arrHashMaps.add(p8);
        arrHashMaps.add(p9);
        arrHashMaps.add(p10);
        return  arrHashMaps;
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
