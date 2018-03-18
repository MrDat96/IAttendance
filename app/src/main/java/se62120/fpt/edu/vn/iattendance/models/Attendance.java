package se62120.fpt.edu.vn.iattendance.models;

/**
 * Created by MrDat on 18/03/2018.
 */

public class Attendance {
    private Student student;
    private AttendanceStatus status;

    public Attendance(Student student, AttendanceStatus status) {
        this.student = student;
        this.status = status;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
