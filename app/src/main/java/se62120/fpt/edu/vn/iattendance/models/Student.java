package se62120.fpt.edu.vn.iattendance.models;

import java.io.Serializable;

/**
 * Created by MrDat on 18/03/2018.
 */

public class Student implements Serializable{
    private String id;
    private String name;
    private String email;
    private String avatarSrc;

    public Student(String id, String name, String email, String avatarSrc) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarSrc = avatarSrc;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }
}
