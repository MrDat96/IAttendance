package se62120.fpt.edu.vn.iattendance.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedFetchAttendancesListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedScanFaceAttendancesListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedUpdateAttendancesLisnter;
import se62120.fpt.edu.vn.iattendance.interfaces.ITakeAttendanceView;
import se62120.fpt.edu.vn.iattendance.models.Attendance;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.FetchAttendancesInteractor;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.UpdateAttendancesInteractor;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.ScanFacesAttendanceInteractor;

/**
 * Created by MrDat on 18/03/2018.
 */

public class TakeAttendancePresenter implements IOnFinishedFetchAttendancesListener, IOnFinishedUpdateAttendancesLisnter
        , IOnFinishedScanFaceAttendancesListener {

    TimeTable timeTable;

    ITakeAttendanceView view;
    FetchAttendancesInteractor interactorFetchAttendances;
    UpdateAttendancesInteractor interactorUpdateAttendaces;
    ScanFacesAttendanceInteractor interactorScanFacesAttendances;
    //ScanFacesAttendaceVolleyInteractor interactorScanFaceAttendanceVolley;

    public TakeAttendancePresenter(ITakeAttendanceView view) {
        this.view = view;
        interactorFetchAttendances = new FetchAttendancesInteractor(this);
        interactorUpdateAttendaces = new UpdateAttendancesInteractor(this);
        interactorScanFacesAttendances = new ScanFacesAttendanceInteractor(this);
    }

    public TakeAttendancePresenter(ITakeAttendanceView view, Context context) {
        this.view = view;
        interactorFetchAttendances = new FetchAttendancesInteractor(this);
        interactorUpdateAttendaces = new UpdateAttendancesInteractor(this);
        interactorScanFacesAttendances = new ScanFacesAttendanceInteractor(this);
        //interactorScanFaceAttendanceVolley = new ScanFacesAttendaceVolleyInteractor(this, context);
    }

    public void fecthAttendance(String token, TimeTable timeTable) {
        this.timeTable = timeTable;
        interactorFetchAttendances.fecthAttendance(token, timeTable.getId() + "");
    }

    public void updateAttendances(String token, SlotAttendance slotAttendance) {
        String data = "[";
        for (int i = 0; i < slotAttendance.getAttendances().size(); i++) {
            data += "{";
            data += "\"student_id\":\"" + slotAttendance.getAttendances().get(i).getStudent().getId() + "\",";
            data += "\"status_attendance_id\":" + slotAttendance.getAttendances().get(i).getStatus().getId();
            data += "}";
            if (i != slotAttendance.getAttendances().size() - 1) {
                data += ",";
            }
        }
        data += "]";
        Log.v(config.AppTag, "Data update attendances JSON: " + data);
        interactorUpdateAttendaces.updateAttendance(token,  slotAttendance.getTimeTable().getId()+"", data);
    }

    public void scanFacesAttendance(String token, String timeTableId, String[] imagePaths) {
        interactorScanFacesAttendances.scanFace(token, timeTableId, imagePaths);
        //interactorScanFaceAttendanceVolley.scanFace(token, timeTableId, imagePaths);
        //new MyAsyncTask(token, timeTableId, imagePaths).execute();
    }

    @Override
    public void onFetchAttendanceSuccess(ArrayList<Attendance> attendances) {
        view.onFetchAttendanceSuccess(new SlotAttendance(timeTable, attendances));
    }

    @Override
    public void onFetchAttendanceFail() {
        view.onFetchAttendanceFail();
    }

    @Override
    public void OnUpdateAttendanceSuccess(int code, String message) {
        view.onUpdateAttendanceSuccess(code, message);
    }

    @Override
    public void OnUpdateAttendanceFail(int code, String message) {
        view.onUpdateAttendanceFail(code, message);
    }

    @Override
    public void onFinishedScanFaceSuccess() {
        view.onUploadScanImagesSuccess();
    }

    @Override
    public void onFinishedScanFaceFail() {
        view.onUploadScanImagesFail();
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        String token;
        String timeTableId;
        String[] imagePaths;

        public MyAsyncTask(String token, String timeTableId, String[] imagePaths) {
            this.token = token;
            this.timeTableId = timeTableId;
            this.imagePaths = imagePaths;
        }

        @Override
        protected void onPreExecute() {
            // Runs on UI thread- Any code you wants
            // to execute before web service call. Put it here.
            // Eg show progress dialog
        }

        @Override
        protected String doInBackground(String... params) {
//            try {
////                String charset = "UTF-8";
////                File file = new File(imagePaths[0]);
////                try {
////                    MultipartUtility multipart = new MultipartUtility("https://iattendance.azurewebsites.net/api/Attendance/Update/StatusAttendance/StudentList/FaceScan", charset);
////
////                    multipart.addHeaderField("Authorization", token);
////
////                    multipart.addFormField("time_table_id", "1");
////                    multipart.addFormField("Authorization", token);
////
////                    multipart.addFilePart("fileUpload.jpg", file);
////
////                    List<String> response = multipart.finish();
////
////                    System.out.println("SERVER REPLIED:");
////
////                    for (String line : response) {
////                        Log.v(config.AppTag, line);
////                    }
////                } catch (IOException ex) {
////                    Log.v(config.AppTag, ex.getMessage());
////                }
//                HttpClient httpclient = HttpClients.createDefault();
//                ArrayList<String> arrayListUrl = new ArrayList();
//                arrayListUrl.add(imagePaths[0]);
//
//                URIBuilder builder = new URIBuilder("https://iattendance.azurewebsites.net/api/Attendance/Update/StatusAttendance/StudentList/FaceScan");
//
//                builder.setParameter("time_table_id", "1");
//
//                URI uri = builder.build();
//                HttpPost request = new HttpPost(uri);
//                //request.setHeader("Content-Type", "application/json; charset=utf-8");
//                Log.v(config.AppTag, "Vinh Token: " + token);
//                request.setHeader("Authorization", token);
//
//                for (int index = 0; index < arrayListUrl.size(); index++) {
//                    File file = new File(arrayListUrl.get(index));
//                    FileEntity reqEntity = new FileEntity(file, "multipart/form-data");
//                    request.setEntity(reqEntity);
//                    HttpResponse response = httpclient.execute(request);
//                    response.setHeader("Content-Type", "application/json; charset=utf-8");
//                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//                    StringBuilder sb = new StringBuilder("");
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        sb.append(line);
//                    }
//                    String json = sb.toString();
//                    Log.v(config.AppTag, "Vinh response:" + json);
////                    JSONArray jsonArray = new JSONArray(json);
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject jsonObject = jsonArray.getJSONObject(i);
////                        faceId = jsonObject.getString("faceId");
////                        arrayList.add(faceId);
////                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String resp) {

            // runs in UI thread - You may do what you want with response
            // Eg Cancel progress dialog - Use result
        }

    }
}
