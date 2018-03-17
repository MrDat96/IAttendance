package se62120.fpt.edu.vn.iattendance.models;

/**
 * Created by MrDat on 18/03/2018.
 */

public class TakeAttendanceStatus {
    private int id;
    private String name;

    public TakeAttendanceStatus(int id, String name) {
        this.id = id;
        this.name = name;
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
