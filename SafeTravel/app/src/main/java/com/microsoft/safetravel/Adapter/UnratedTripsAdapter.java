package com.microsoft.safetravel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microsoft.safetravel.AsyncTask.DownloadImageTask;
import com.microsoft.safetravel.Listener.DriverSearchResultOnClickListener;
import com.microsoft.safetravel.Listener.RateDriverOnClickListener;
import com.microsoft.safetravel.MainActivity;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Modal.DriverSearchResult;
import com.microsoft.safetravel.Modal.UnratedTrips;
import com.microsoft.safetravel.R;
import com.microsoft.safetravel.UnratedTripsActivity;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import java.util.List;

/**
 * Created by sikanted on 7/29/2015.
 */
public class UnratedTripsAdapter extends ArrayAdapter<UnratedTrips> {

    public UnratedTripsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UnratedTripsAdapter(Context context, int resource, List<UnratedTrips> items) {
        super(context, resource, items);
    }


    /*
    imageOfDriver
            driverName
    dateOfTrip
            rateDriver
    */

    public View getView(int position,  View view, ViewGroup parent)
    {
        View newView = view;

        if (newView == null) {
            LayoutInflater layoutInflater= LayoutInflater.from(getContext());
            newView = layoutInflater.inflate(R.layout.unrated_trips_list, null);
        }
        UnratedTrips unratedTrips = (UnratedTrips)getItem(position);

        if (unratedTrips != null) {
            ImageView imageOfDriver = (ImageView)newView.findViewById(R.id.imageOfDriver);
            TextView driverName = (TextView)newView.findViewById(R.id.nameOfDriver);
            TextView dateOfTrip = (TextView)newView.findViewById(R.id.dateOfTrip);
            Button rateDriver = (Button)newView.findViewById(R.id.rateDriver);

            if(imageOfDriver != null) {
                String imageUrl = String.format(Constant.imageOfReviewer, unratedTrips.getDriverId());
                imageUrl = Constant.apiUrl.concat(imageUrl);
                new DownloadImageTask(imageOfDriver).execute(imageUrl);
            }

            if (driverName != null) {
                driverName.setText(unratedTrips.getDriverName());
            }

            if (dateOfTrip != null) {
                dateOfTrip.setText(Util.convertUtcToDisplayTimeFormat(unratedTrips.getReviewTime()));
            }

            if (rateDriver != null) {
                moveToRateDriverPage(unratedTrips, rateDriver);
            }
        }
        return newView;
    }

    private void moveToRateDriverPage(UnratedTrips unratedTrips,  Button rateDrive){
        UnratedTripsActivity baseActivity = Util.as(UnratedTripsActivity.class, getContext());
        rateDrive.setOnClickListener(new RateDriverOnClickListener(unratedTrips, baseActivity));
    }
}
