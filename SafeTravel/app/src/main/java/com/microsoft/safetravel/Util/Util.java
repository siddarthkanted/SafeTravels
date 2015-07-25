package com.microsoft.safetravel.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.microsoft.safetravel.R;

/**
 * Created by sikanted on 7/24/2015.
 */
public class Util {

    public static void pushNotificationWithOnClick(Context context, String notificationTitle, String notificationMessage,  Class<?> cls) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher, notificationMessage, System.currentTimeMillis());
        Intent notificationIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(10001, notification);
    }

}
