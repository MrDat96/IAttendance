package se62120.fpt.edu.vn.iattendance.models.fcm;

/**
 * Created by MrDat on 26/03/2018.
 */

public class Data {
    private String title;
    private String message;

    public Data() {
    }

    public Data(String title, String message) {

        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
