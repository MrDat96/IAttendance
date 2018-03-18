package se62120.fpt.edu.vn.iattendance.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MrDat on 18/03/2018.
 */

public class SlotAttendance implements Serializable{
    private TimeTable timeTable;
    private ArrayList<Attendance> attendances;


    public SlotAttendance() {
    }

    public SlotAttendance(TimeTable timeTable, ArrayList<Attendance> attendances) {
        this.timeTable = timeTable;
        this.attendances = attendances;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(ArrayList<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public String toString() {
        return "SlotAttendance{" +
                "attendances=" + attendances +
                '}';
    }
}
