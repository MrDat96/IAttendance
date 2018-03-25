package se62120.fpt.edu.vn.iattendance.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MrDat on 17/03/2018.
 */

public class Teacher implements Serializable{
    private String id;
    private String name;
    private Date DOB;
    private String email;
    private String phone;

    public Teacher(String id) {
        this.id = id;
    }

    public Teacher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher(String id, String name, Date DOB, String email, String phone) {
        this.id = id;
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
