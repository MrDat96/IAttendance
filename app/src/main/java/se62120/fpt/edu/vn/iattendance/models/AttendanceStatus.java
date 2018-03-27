package se62120.fpt.edu.vn.iattendance.models;

import java.io.Serializable;

/**
 * Created by MrDat on 18/03/2018.
 */

public class AttendanceStatus implements Serializable {
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

    @Override
    public String toString() {
        return "AttendanceStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
