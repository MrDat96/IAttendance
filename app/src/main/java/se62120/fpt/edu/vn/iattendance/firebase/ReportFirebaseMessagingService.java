package se62120.fpt.edu.vn.iattendance.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.views.activities.LoginActivity;
import se62120.fpt.edu.vn.iattendance.views.activities.NavigationTeacherActivity;

/**
 * Created by MrDat on 23/03/2018.
 */

public class ReportFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(config.AppTag, "From: " + remoteMessage.getFrom());
        Log.v(config.AppTag, "From: " + remoteMessage.getData().get("title"));

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        Intent reportActivity = new Intent(getApplicationContext(), NavigationTeacherActivity.class);
        PendingIntent report = PendingIntent.getActivity(this, 0, reportActivity, 0);
        Notification n  = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.profile_icon)
                .setContentIntent(report)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_menu_message, "Message", report).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }
}
