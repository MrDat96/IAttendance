package se62120.fpt.edu.vn.iattendance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MrDat on 17/03/2018.
 */

public class TimeTable implements Serializable{
    private int id;
    private Date date;
    private Slot slot;
    private Teacher teacher;
    private StudentGroup studentGroup;
    private Room room;
    private Campus campus;
    private Course course;
    private TimeTableStatus timeTableStatus;
    private TakeAttendanceStatus takeAttendanceStatus;

    public TimeTable() {
    }

    public TimeTable(int id, Date date, Slot slot, Teacher teacher, StudentGroup studentGroup, Room room, Campus campus, Course course, TimeTableStatus timeTableStatus) {
        this.id = id;
        this.date = date;
        this.slot = slot;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
        this.room = room;
        this.campus = campus;
        this.course = course;
        this.timeTableStatus = timeTableStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public TimeTableStatus getTimeTableStatus() {
        return timeTableStatus;
    }

    public void setTimeTableStatus(TimeTableStatus timeTableStatus) {
        this.timeTableStatus = timeTableStatus;
    }

    public TakeAttendanceStatus getTakeAttendanceStatus() {
        return takeAttendanceStatus;
    }

    public void setTakeAttendanceStatus(TakeAttendanceStatus takeAttendanceStatus) {
        this.takeAttendanceStatus = takeAttendanceStatus;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "id=" + id +
                ", date=" + date +
                ", slot=" + slot +
                ", teacher=" + teacher +
                ", studentGroup=" + studentGroup +
                ", room=" + room +
                ", campus=" + campus +
                ", course=" + course +
                ", timeTableStatus=" + timeTableStatus +
                ", takeAttendanceStatus=" + takeAttendanceStatus +
                '}';
    }
}
