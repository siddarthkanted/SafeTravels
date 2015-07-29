package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import com.microsoft.safetravel.BaseActivity;
import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.MainActivity;
import com.microsoft.safetravel.RateDriverActivity;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

/**
 * Created by sikanted on 7/28/2015.
 */
public class SearchDriversTask extends AsyncTask<String, Integer, String> {

    private Activity searchDriversTask;
    private String driverName;

    public SearchDriversTask(Activity searchDriversTask, String driverName)
    {
        this.searchDriversTask = searchDriversTask;
        this.driverName = driverName;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        return HttpUtil.SendHttpGetRequest(url);
    }

    @Override
    protected void onPostExecute(String result)
    {
        MainActivity baseActivity = Util.as(MainActivity.class, searchDriversTask);
            if(baseActivity != null){
                //baseActivity.updateDriverNameCarNumberPlateTextView();
                baseActivity.updateDriversList(result);
            }
    }
}
