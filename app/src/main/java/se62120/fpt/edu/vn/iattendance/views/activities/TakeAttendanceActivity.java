package se62120.fpt.edu.vn.iattendance.views.activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.views.RVTeacherManualTakeAdapter;

public class TakeAttendanceActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    private int currentIndexImage = -1;
    private int currentChoiceImage = -1;
    String dir = "";
    String jsonString = "";
    //@BindView(R.id.btnTakePictures) Button _btntakePictures;
    @BindView(R.id.btnPickerImages) Button _btnPickerImages;
    @BindView(R.id.rvManualTaken) RecyclerView _rvManualTaken;
    @BindView(R.id.ivPicker1) ImageView _ivPicker1;
    @BindView(R.id.ivPicker2) ImageView _ivPicker2;
    @BindView(R.id.ivPicker3) ImageView _ivPicker3;

    RVTeacherManualTakeAdapter _rvTeacherManualTakeAdapter;
    ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_take_attendance);
        getSupportActionBar().setTitle("Take Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) _ivPicker1.getLayoutParams();
        params.width = 180;
        params.height = 180;
        _ivPicker1.setLayoutParams(params);
        _ivPicker2.setLayoutParams(params);
        _ivPicker3.setLayoutParams(params);

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

    @OnClick(R.id.btnPickerImages)
    void pickImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
    }

    //@OnClick(R.id.btnTakePictures) void OnClickTakePickerButton(View view) {takePictures();}
    @OnClick(R.id.ivPicker1) void OnClickPickerImage1(View view) {currentChoiceImage = 0; takePictures();}
    @OnClick(R.id.ivPicker2) void OnClickPickerImage2(View view) {currentChoiceImage = 1; takePictures();}
    @OnClick(R.id.ivPicker3) void OnClickPickerImage3(View view) {currentChoiceImage = 2; takePictures();}

    void takePictures() {
        //String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";//
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdirs();
        }
        String file = dir + getTimeStamp() + ".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri outputFileUri = Uri.fromFile(newfile);
        mCurrentPhotoPath = newfile.getAbsolutePath();
        // dispatch Take picture intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timeStamp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            pickImageHandler(resultCode, data);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            takedPictureHandler(resultCode, data);
        }
    }

    void takedPictureHandler(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Log.v(config.AppTag, "MR DAT: " + mCurrentPhotoPath);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            int index = -1;
            if (currentChoiceImage == -1) {
                currentIndexImage++;
                if (currentIndexImage == 3) currentIndexImage = 0;
                index = currentIndexImage;
            } else {
                index = currentChoiceImage;
                currentChoiceImage = -1;
            }

            switch (index) {
                case 0:
                    _ivPicker1.setImageURI(contentUri);
                    break;
                case 1:
                    _ivPicker2.setImageURI(contentUri);
                    break;
                case 2:
                    _ivPicker3.setImageURI(contentUri);
                    break;
            }

        }
    }

    void pickImageHandler(int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                int currentItem = 0;
                while(currentItem < count) {
                    Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                    if (currentItem == 0) {
                        _ivPicker1.setImageURI(imageUri);
                    } else if (currentItem == 1){
                        _ivPicker2.setImageURI(imageUri);
                    } else if (currentItem == 2) {
                        _ivPicker3.setImageURI(imageUri);
                    }
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                    Log.v(config.AppTag, "Show up");
                    currentItem = currentItem + 1;
                }
            } else if(data.getData() != null) {
                Uri imageUri = data.getData();
                _ivPicker1.setImageURI(imageUri);
                //String imagePath = data.getData().getPath();
                //Log.v(config.AppTag, "Show off");

                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
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

    // Back button enable
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
