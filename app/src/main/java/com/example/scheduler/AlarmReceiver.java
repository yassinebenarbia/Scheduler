package com.example.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * When the <i>AlarmManager </i>triggers the alarm the <i>AlarmReceiver</i>, it sends a
 * message to the system, which is received  by the <i>AlarmReceiver</i>by the
 * <i>onReceivee </i>method
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String TAG = "Alarm is Set";
    private String msg;
    private String action;

    @Override
    public void onReceive(Context context, Intent intent) {
        action = "com.example.scheduler";
        msg = "Alarm goes on!";
        if (intent.getAction().equals(action)) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
            startNotification(msg,hashCode(),context);
        } else {
            Log.i(TAG, action);
        }
    }

    public void startNotification(String msg, int id, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ScheduledAlarm")
                                                     .setSmallIcon(R.drawable.ic_launcher_background)
                                                     .setContentTitle("Scheduled Alarm")
                                                     .setContentText(msg)
                                                     .setAutoCancel(true)
                                                     .setDefaults(NotificationCompat.DEFAULT_ALL)
                                                     .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //  ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(id, builder.build());
    }
}
