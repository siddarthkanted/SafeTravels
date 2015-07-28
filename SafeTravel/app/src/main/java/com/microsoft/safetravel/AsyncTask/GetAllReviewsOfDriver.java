package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.ListOfDriversReviewActivity;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONObject;

/**
 * Created by sikanted on 7/27/2015.
 */
public class GetAllReviewsOfDriver extends AsyncTask<String, Integer, String> {

    private Activity getAllReviewsOfDriver;

    public GetAllReviewsOfDriver(Activity getAllReviewsOfDriver)
    {
        this.getAllReviewsOfDriver = getAllReviewsOfDriver;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        return HttpUtil.SendHttpGetRequest(url);
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (null != result) {
            ListOfDriversReviewActivity listOfDriversReviewActivity = Util.as(ListOfDriversReviewActivity.class, getAllReviewsOfDriver);
            if(listOfDriversReviewActivity!=null) {
                listOfDriversReviewActivity.updateList(result);
            }else{
                DriverDetails driverDetails = Util.as(DriverDetails.class, getAllReviewsOfDriver);
                if(driverDetails != null){
                    driverDetails.updateList(result);
                }
            }
        }
    }
}
