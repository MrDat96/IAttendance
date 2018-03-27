package se62120.fpt.edu.vn.iattendance.models.reports;

import se62120.fpt.edu.vn.iattendance.models.Student;
import se62120.fpt.edu.vn.iattendance.models.Teacher;
import se62120.fpt.edu.vn.iattendance.models.TimeTable;

/**
 * Created by MrDat on 26/03/2018.
 */

public class Report {
    private Student student;
    private Teacher teacher;
    private String topic;
    private String description;
    private TimeTable timeTable;
    private ReportStatus reportStatus;

    public Report() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }
}
