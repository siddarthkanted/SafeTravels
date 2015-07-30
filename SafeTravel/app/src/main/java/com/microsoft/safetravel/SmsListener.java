package com.microsoft.safetravel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.telephony.SmsMessage;

import com.microsoft.safetravel.AsyncTask.ParseAllSmsTask;
import  com.microsoft.safetravel.SmsParser.SmsParser;
import  com.microsoft.safetravel.SmsParser.ISmsParser;
import  com.microsoft.safetravel.SmsParser.ParsedSms;

import com.microsoft.safetravel.Util.Constant;
import  com.microsoft.safetravel.Util.Util;


/**
 * Created by sikanted on 7/24/2015.
 */
public class SmsListener extends BroadcastReceiver {

    private String getNotificationMessage(Context context, ParsedSms parsedSms){
        String message = Constant.notificationMessage;
        message = String.format(message, "25%", parsedSms.getDriverName(), Util.convertCurrentTimeToDisplayFormat());
        return message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") || intent.getAction().equals("android.permission.RECEIVE_SMS")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        ISmsParser sms =  new SmsParser();
                        ParsedSms parsedSms = sms.parseSms(msgBody);
                        if(parsedSms != null) {
                            Intent notificationIntent = new Intent(context, DriverDetails.class);
                          //  Util.pushNotificationWithOnClick(context, context.getResources().getString(R.string.app_name), getNotificationMessage(context, parsedSms), notificationIntent);
                            Util.pushNotificationWithOnClickMultiline(context, context.getResources().getString(R.string.app_name), getNotificationMessage(context, parsedSms), notificationIntent);
                        }
                    }
                }catch(Exception e){
                }
            }
        }
    }


}
 