package com.microsoft.safetravel.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.microsoft.safetravel.BaseActivity;
import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.Modal.DriverSearchResult;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

/**
 * Created by sikanted on 7/29/2015.
 */
public class DriverSearchResultOnClickListener implements View.OnClickListener {

    private DriverSearchResult driverSearchResult;
    private Activity searchDriversTask;

    public DriverSearchResultOnClickListener(DriverSearchResult driverSearchResult, Activity searchDriversTask) {
        this.driverSearchResult = driverSearchResult;
        this.searchDriversTask = searchDriversTask;
    }

    @Override
    public void onClick(View v)
    {
        moveToDriverDetailsPage();
    }

    public void moveToDriverDetailsPage() {
        BaseActivity baseActivity = Util.as(BaseActivity.class, searchDriversTask);
        Intent intent = new Intent(baseActivity, DriverDetails.class);
        intent.putExtra(Constant.driverId, driverSearchResult.getDriverId());
        baseActivity.startActivity(intent);
    }
}
