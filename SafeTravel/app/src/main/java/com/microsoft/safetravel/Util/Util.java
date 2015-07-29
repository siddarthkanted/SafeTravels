package com.microsoft.safetravel.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.R;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sikanted on 7/24/2015.
 */
public class Util {

    public static void pushNotificationWithOnClick(Context context, String notificationTitle, String notificationMessage,  Intent notificationIntent) {
        //android.support.v4.app.NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(notificationMessage);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.safe_trip, notificationMessage, System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(10001, notification);
    }

    public static void pushNotificationWithOnClickMultiline(Context context, String notificationTitle, String notificationMessage,  Intent notificationIntent) {
        int icon = R.drawable.safe_trip;

        int mNotificationId = 001;

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(notificationTitle).setWhen(0)
                .setAutoCancel(false)
                .setContentTitle(notificationTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationMessage))
                .setContentIntent(resultPendingIntent)
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.safe_trip))
                .setContentText(notificationMessage).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }

    public static View getSelectedRadioButton(RadioGroup radioButtonGroup) {
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        return radioButton;
    }

    public static String getString(String source, String destination) {
       return null != destination ?  destination : source;
    }

    public static <T> T as(Class<T> clazz, Object o){
        if(clazz.isInstance(o)){
            return clazz.cast(o);
        }
        return null;
    }

    public static Boolean getBoolean(String text){
        if(text.equals(Constant.yes)){
            return true;
        }
        return false;
    }

    public static String convertCurrentTimeToDisplayFormat(){
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constant.utcTimeFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(Constant.displatTimeFormat).format(calendar.getTime()).toString();
    }

    public static String convertUtcToDisplayTimeFormat(String utcTime){
        Calendar calendar = Calendar.getInstance();
        try {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.utcTimeFormat);
            calendar.setTime(sdf.parse(utcTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(Constant.displatTimeFormat).format(calendar.getTime()).toString();
    }
}
