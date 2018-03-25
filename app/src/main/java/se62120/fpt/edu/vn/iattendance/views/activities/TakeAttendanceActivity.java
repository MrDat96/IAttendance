package se62120.fpt.edu.vn.iattendance.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.ITakeAttendanceView;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.presenters.TakeAttendancePresenter;
import se62120.fpt.edu.vn.iattendance.views.adapters.RVTeacherTakeAttendanceAdapter;

public class TakeAttendanceActivity extends AppCompatActivity implements ITakeAttendanceView {
    public static final int PICK_IMAGE = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    private int currentChoiceImage = -1;

    @BindView(R.id.btnPickerImages) Button _btnPickerImages;
    @BindView(R.id.rvManualTaken) RecyclerView _rvManualTaken;
    @BindView(R.id.ivPicker1) ImageView _ivPicker1;
    @BindView(R.id.ivPicker2) ImageView _ivPicker2;
    @BindView(R.id.ivPicker3) ImageView _ivPicker3;
    @BindView(R.id.btnSubmit) Button _btnSumit;
    @BindView(R.id.btnSaveAttendances) Button _btnSaveAttendance;

    RVTeacherTakeAttendanceAdapter _rvTeacherManualTakeAdapter;
    TakeAttendancePresenter presenter;
    ProgressDialog mProgressDialog;

    SlotAttendance slotAttendance = null;
    TimeTable timeTable;
    String token = null;
    String mCurrentPhotoPath;

    String[] imagePaths = {null, null, null};

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
            timeTable = (TimeTable) extras.getSerializable("TimeTable");
            Log.v(config.AppTag, "Time Table : " + timeTable);
        } else {
            Toast.makeText(getApplicationContext(), "No extra", Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        _rvManualTaken.setLayoutManager(llm);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.share_preference), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "Not found!");
        presenter = new TakeAttendancePresenter(this, getBaseContext());
        presenter.fecthAttendance("bearer " + token, timeTable);
    }

    @OnClick(R.id.btnPickerImages) void pickImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @OnClick(R.id.ivPicker1) void OnClickPickerImage1(View view) {
        currentChoiceImage = 0;
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.ivPicker2) void OnClickPickerImage2(View view) {
        currentChoiceImage = 1;
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.ivPicker3) void OnClickPickerImage3(View view) {
        currentChoiceImage = 2;
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.btnSubmit) void onScanFaceAttendanceImages() {
        Log.v(config.AppTag, "On scanning face...");
        showProgressDialog();
        presenter.scanFacesAttendance("bearer " + token, slotAttendance.getTimeTable().getId()+"", imagePaths);
    }

    @OnClick(R.id.btnSaveAttendances)
    void onSubmitAttendances() {
        Log.v(config.AppTag, slotAttendance.toString());
        showProgressDialog();
        presenter.updateAttendances("bearer " + token, slotAttendance);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Log.v(config.AppTag, "On dispatch Take Picture Intent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.v(config.AppTag, "Create file fail : " + ex.getStackTrace() + "\n" + ex.getMessage());
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                String authorities = getApplicationContext().getPackageName() + ".fileprovider";
                Uri photoURI = FileProvider.getUriForFile(this,
                        authorities,
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            pickImageHandler(resultCode, data);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            takedPictureHandlerOnResult(resultCode, data);
        }
    }

    void takedPictureHandlerOnResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            Log.v(config.AppTag, "File stored: " + mCurrentPhotoPath);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);

            switch (currentChoiceImage) {
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
            galleryAddPic();
            imagePaths[currentChoiceImage] = mCurrentPhotoPath;
            currentChoiceImage = -1;
            Log.v(config.AppTag, "Path:" + imagePaths[0]);
        }
    }
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    void pickImageHandler(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                int currentItem = 0;
                while (currentItem < count) {
                    Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                    if (currentItem == 0) {
                        _ivPicker1.setImageURI(imageUri);
                        imagePaths[0] = getRealPathFromURI_API19(getApplicationContext(), imageUri);
                    } else if (currentItem == 1) {
                        _ivPicker2.setImageURI(imageUri);
                        imagePaths[1] = getRealPathFromURI_API19(getApplicationContext(), imageUri);
                    } else if (currentItem == 2) {
                        _ivPicker3.setImageURI(imageUri);
                        imagePaths[2] = getRealPathFromURI_API19(getApplicationContext(), imageUri);
                    }
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                    Log.v(config.AppTag, "Show up");
                    currentItem = currentItem + 1;
                }
                Log.v(config.AppTag, "Path:" + imagePaths[0]);
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                _ivPicker1.setImageURI(imageUri);

                imagePaths[0] = getRealPathFromURI_API19(getApplicationContext(), imageUri);
                Log.v(config.AppTag, "Path:" + imagePaths[0]);
                //String imagePath = data.getData().getPath();
                //Log.v(config.AppTag, "Show off");
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // Back button enable
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onFetchAttendanceSuccess(SlotAttendance slotAttendance) {
        Log.v(config.AppTag, "On fetch data sucess");
        this.slotAttendance = slotAttendance;
        _rvTeacherManualTakeAdapter = new RVTeacherTakeAttendanceAdapter(slotAttendance);
        _rvManualTaken.setAdapter(_rvTeacherManualTakeAdapter);
        hideProgressDialog();
    }

    @Override
    public void onFetchAttendanceFail() {
        Log.v(config.AppTag, "On fetch data failed");
        hideProgressDialog();
    }

    @Override
    public void onUpdateAttendanceSuccess(int code, String message) {
        hideProgressDialog();
        Log.v(config.AppTag, "Update attendances Success!");
        Toast.makeText(getApplicationContext(),"Update successful!", Toast.LENGTH_SHORT).show();
        //presenter.fecthAttendance(token, timeTable);
    }

    @Override
    public void onUpdateAttendanceFail(int code, String message) {
        hideProgressDialog();
        Log.v(config.AppTag, "Update attendances fail!");
        Toast.makeText(getApplicationContext(),"Update fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadScanImagesSuccess() {
        Log.v(config.AppTag, "Scan face successful!");
        //hideProgressDialog();
        //Fetched attendances again
        Toast.makeText(getApplicationContext(), "Scan successful", Toast.LENGTH_SHORT).show();
        presenter.fecthAttendance("bearer " + token, timeTable);
    }

    @Override
    public void onUploadScanImagesFail() {
        Log.v(config.AppTag, "Scan face fail!");
        Toast.makeText(getApplicationContext(), "Scan fail", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
