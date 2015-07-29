package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;

import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.ListOfDriversReviewActivity;
import com.microsoft.safetravel.UnratedTripsActivity;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

/**
 * Created by sikanted on 7/29/2015.
 */
public class GetAllUnratedTrips extends AsyncTask<String, Integer, String> {
    private Activity getAllUnratedTrips;

    public GetAllUnratedTrips(Activity getAllUnratedTrips)
    {
        this.getAllUnratedTrips = getAllUnratedTrips;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        return HttpUtil.SendHttpGetRequest(url);
    }

    @Override
    protected void onPostExecute(String result)
    {
                UnratedTripsActivity unratedTripsActivity = Util.as(UnratedTripsActivity.class, getAllUnratedTrips);
                if(unratedTripsActivity != null){
                    unratedTripsActivity.updateAllUnratedTrips(result);
                }
    }
}
