package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;

import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

/**
 * Created by sikanted on 7/28/2015.
 */
public class GetLastTravelledDateTask extends AsyncTask<String, Integer, String> {

    private Activity getLastTravelledDateTask;

    public GetLastTravelledDateTask(Activity getLastTravelledDateTask)
    {
        this.getLastTravelledDateTask = getLastTravelledDateTask;
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
            DriverDetails driverDetails = Util.as(DriverDetails.class, getLastTravelledDateTask);
            if(driverDetails != null){
                driverDetails.updateLastTravelledDate(result);
            }
        }
    }
}
