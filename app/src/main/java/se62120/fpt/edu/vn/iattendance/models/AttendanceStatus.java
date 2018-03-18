package se62120.fpt.edu.vn.iattendance.models;

/**
 * Created by MrDat on 18/03/2018.
 */

public class AttendanceStatus {
    private int id;
    private String name;

    public AttendanceStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AttendanceStatus() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
