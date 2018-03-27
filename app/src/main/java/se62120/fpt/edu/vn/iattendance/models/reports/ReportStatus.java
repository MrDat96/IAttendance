package se62120.fpt.edu.vn.iattendance.models.reports;

/**
 * Created by MrDat on 26/03/2018.
 */

public class ReportStatus {
    private int id;
    private String name;

    public ReportStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ReportStatus() {
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
