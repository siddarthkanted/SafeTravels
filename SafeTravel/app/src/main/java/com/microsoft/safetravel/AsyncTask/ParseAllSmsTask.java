package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.MainActivity;
import com.microsoft.safetravel.R;
import com.microsoft.safetravel.SmsParser.ISmsParser;
import com.microsoft.safetravel.SmsParser.ParsedSms;
import com.microsoft.safetravel.SmsParser.SmsParser;
import com.microsoft.safetravel.UnratedTripsActivity;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sikanted on 7/28/2015.
 */
public class ParseAllSmsTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private String reviewerId;

    public ParseAllSmsTask(Context context, String reviewerId)
    {
        this.context = context;
        this.reviewerId = reviewerId;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(inboxUri, new String[] {"body"}, null, null, null);
        if(cursor == null || cursor.getCount() == 0) {
            return "0";
        }

        ISmsParser smsParser = new SmsParser();
        JSONArray jsonArray = new JSONArray();
        while(cursor.moveToNext()) {
            String body = cursor.getString(0);

            ParsedSms parsedSms = smsParser.parseSms(body);
            if(parsedSms != null) {
                JSONObject json = createJson(parsedSms);
                jsonArray.put(json);
            }
        }

        if(jsonArray.length() > 0) {
            String url = Constant.apiUrl.concat(Constant.addTripPath);
            HttpUtil.SendHttpPostRequest(url, jsonArray);
        }

        return String.valueOf(jsonArray.length());
    }

    private JSONObject createJson(ParsedSms parsedSms) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Constant.tripId, parsedSms.getBookingId());
                jsonObject.put(Constant.reviewerId, reviewerId);
                jsonObject.put(Constant.driverName, parsedSms.getDriverName());
                jsonObject.put(Constant.vehicleNumber, parsedSms.getVehicleNumber());
                jsonObject.put(Constant.contactNumber, parsedSms.getDriverMobile());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
    }

    @Override
    protected void onPostExecute(String result)
    {
        int numberOfUnratedTrips = Integer.parseInt(result);
        if(numberOfUnratedTrips <= 0) {
            return;
        }

        String message = String.format(context.getResources().getString(R.string.unratedTripNotification), result);

        Intent notificationIntent = new Intent(context, UnratedTripsActivity.class);
        notificationIntent.putExtra(Constant.reviewerId, Constant.defaultReviewerId);
       // Util.pushNotificationWithOnClick(context, context.getResources().getString(R.string.app_name), message, notificationIntent);
        Util.pushNotificationWithOnClickMultiline(context, context.getResources().getString(R.string.app_name), message, notificationIntent);
    }
}
