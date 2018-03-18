package se62120.fpt.edu.vn.iattendance.presenters;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedFetchAttendancesListener;
import se62120.fpt.edu.vn.iattendance.interfaces.IOnFinishedUpdateAttendancesLisnter;
import se62120.fpt.edu.vn.iattendance.interfaces.ITakeAttendanceView;
import se62120.fpt.edu.vn.iattendance.models.Attendance;
import se62120.fpt.edu.vn.iattendance.models.AttendanceStatus;
import se62120.fpt.edu.vn.iattendance.models.RetrofitSupport;
import se62120.fpt.edu.vn.iattendance.models.SlotAttendance;
import se62120.fpt.edu.vn.iattendance.models.Student;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.FetchAttendancesInteractor;
import se62120.fpt.edu.vn.iattendance.presenters.interactor.UpdateAttendancesInteractor;
import se62120.fpt.edu.vn.iattendance.services.TakeAttendanceService;

/**
 * Created by MrDat on 18/03/2018.
 */

public class TakeAttendancePresenter implements IOnFinishedFetchAttendancesListener, IOnFinishedUpdateAttendancesLisnter{

    TimeTable timeTable;

    ITakeAttendanceView view;
    FetchAttendancesInteractor interactorFetchAttendances;
    UpdateAttendancesInteractor interactorUpdateAttendaces;

    public TakeAttendancePresenter(ITakeAttendanceView view) {
        this.view = view;
        interactorFetchAttendances = new FetchAttendancesInteractor(this);
        interactorUpdateAttendaces = new UpdateAttendancesInteractor(this);
    }

    public void fecthAttendance(String token, TimeTable timeTable) {
        this.timeTable = timeTable;
        interactorFetchAttendances.fecthAttendance(token, timeTable.getId()+"");
    }

    public void updateAttendances(String token, SlotAttendance slotAttendance) {
        String data = "{";
            data += "\"id\":" + slotAttendance.getTimeTable().getId() + ",";
            data += "\"attendance_list\": [";

            for(int i = 0; i < slotAttendance.getAttendances().size(); i++) {
                data += "{";
                data += "\"student_id\":\"" + slotAttendance.getAttendances().get(i).getStudent().getId() + "\",";
                data += "\"status_attendance_id\":" + slotAttendance.getAttendances().get(i).getStatus().getId();
                data += "}";
                if (i != slotAttendance.getAttendances().size() - 1) {
                    data += ",";
                }
            }
            data += "]";
        data += "}";
        Log.v(config.AppTag, "Data update attendances JSON: " + data);
        interactorUpdateAttendaces.updateAttendance(token, data);
    }

    @Override
    public void onFetchAttendanceSuccess(ArrayList<Attendance> attendances ) {
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
        view.onUpdateAttendanceSuccess(code, message);
    }
}
