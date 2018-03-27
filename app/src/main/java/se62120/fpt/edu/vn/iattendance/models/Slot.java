package se62120.fpt.edu.vn.iattendance.models;

import java.io.Serializable;
import java.util.Timer;

/**
 * Created by MrDat on 17/03/2018.
 */

public class Slot implements Serializable{
    private int id;
    private String startTime;
    private String endTime;

    public Slot() {
    }

    public Slot(int id, String startTime, String endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
