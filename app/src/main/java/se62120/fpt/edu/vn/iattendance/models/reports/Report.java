package se62120.fpt.edu.vn.iattendance.models.reports;

import java.util.Date;

import se62120.fpt.edu.vn.iattendance.models.Slot;
import se62120.fpt.edu.vn.iattendance.models.Student;
import se62120.fpt.edu.vn.iattendance.models.Teacher;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;

/**
 * Created by MrDat on 26/03/2018.
 */

public class Report {
    private int id;
    private String sender;
    private String receiver;
    private String topic;
    private String description;
    private Date date;
    private Slot slot;
    private ReportStatus reportStatus;

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public String toString() {
        return "Report{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", slot=" + slot +
                ", reportStatus=" + reportStatus +
                '}';
    }
}
