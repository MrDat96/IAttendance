package se62120.fpt.edu.vn.iattendance.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import se62120.fpt.edu.vn.iattendance.configures.config;

/**
 * Created by MrDat on 23/03/2018.
 */

public class ReportFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(config.AppTag, "From: " + remoteMessage.getFrom());
        Log.d(config.AppTag, "From: " + remoteMessage.getNotification().getBody());
    }
}
