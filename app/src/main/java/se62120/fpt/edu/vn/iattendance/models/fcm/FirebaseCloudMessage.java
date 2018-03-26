package se62120.fpt.edu.vn.iattendance.models.fcm;

/**
 * Created by MrDat on 26/03/2018.
 */

public class FirebaseCloudMessage {
    private String to;
    private Data data;

    public FirebaseCloudMessage(String to, Data data) {
        this.to = to;
        this.data = data;
    }

    public FirebaseCloudMessage() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FirebaseCloudMessage{" +
                "to='" + to + '\'' +
                ", data=" + data +
                '}';
    }
    public String toJSONString() {
        return "{" +
                "\"to\":" + "\"" + this.to + "\"," +
                "\"data\":"+ "{" +
                "\"title\":" + "\"" +  this.data.getTitle() +"\"," +
                "\"message\":" + "\"" +  this.data.getMessage() +"\"" +
                "}"
                + "}";
    }
}
