package com.microsoft.safetravel.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.MainActivity;
import com.microsoft.safetravel.Modal.DriverSearchResult;
import com.microsoft.safetravel.Modal.UnratedTrips;
import com.microsoft.safetravel.RateDriverActivity;
import com.microsoft.safetravel.UnratedTripsActivity;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

/**
 * Created by sikanted on 7/29/2015.
 */
public class RateDriverOnClickListener implements View.OnClickListener {

    private UnratedTrips unratedTrips;
    private Activity searchDriversTask;

    public RateDriverOnClickListener(UnratedTrips unratedTrips, Activity searchDriversTask) {
        this.unratedTrips = unratedTrips;
        this.searchDriversTask = searchDriversTask;
    }

    @Override
    public void onClick(View v)
    {
        moveToRateDriverPage();
    }

    public void moveToRateDriverPage() {
        UnratedTripsActivity baseActivity = Util.as(UnratedTripsActivity.class, searchDriversTask);
        Intent intent = new Intent(baseActivity, RateDriverActivity.class);
        intent.putExtra(Constant.driverId, unratedTrips.getDriverId());
        intent.putExtra(Constant.tripId, unratedTrips.getTripId());
        baseActivity.startActivity(intent);
    }
}
