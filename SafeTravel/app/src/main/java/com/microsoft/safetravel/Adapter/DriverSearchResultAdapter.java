package com.microsoft.safetravel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microsoft.safetravel.AsyncTask.DownloadImageTask;
import com.microsoft.safetravel.BaseActivity;
import com.microsoft.safetravel.DriverDetails;
import com.microsoft.safetravel.Listener.DriverSearchResultOnClickListener;
import com.microsoft.safetravel.Modal.DriverProfile;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Modal.DriverSearchResult;
import com.microsoft.safetravel.R;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import java.util.List;

/**
 * Created by sikanted on 7/29/2015.
 */
public class DriverSearchResultAdapter extends ArrayAdapter<DriverSearchResult> {

    public DriverSearchResultAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DriverSearchResultAdapter(Context context, int resource, List<DriverSearchResult> items) {
        super(context, resource, items);
    }

    /*
    imageOfDriver
            nameOfDriver
    numberOfRatings
            rateDriver
    imageOfCard
   */

    public View getView(int position,  View view, ViewGroup parent)
    {
        View newView = view;

        if (newView == null) {
            LayoutInflater layoutInflater= LayoutInflater.from(getContext());
            newView = layoutInflater.inflate(R.layout.list_of_search_item, null);
        }
        DriverSearchResult driverSearchResult = (DriverSearchResult)getItem(position);

        if (driverSearchResult != null) {
            ImageView imageOfDriver = (ImageView)newView.findViewById(R.id.imageOfDriver);
            ImageView imageOfCard = (ImageView)newView.findViewById(R.id.imageOfCard);
            TextView nameOfDriver = (TextView)newView.findViewById(R.id.nameOfDriver);
            TextView numberOfRatings = (TextView)newView.findViewById(R.id.numberOfRatings);
            LinearLayout linearLayout = (LinearLayout)newView.findViewById(R.id.driverSearchResultLayout);
           // Button rateDriver = (Button)newView.findViewById(R.id.rateDriver);

            if(imageOfDriver != null) {
                String imageUrl = String.format(Constant.imageOfReviewer, driverSearchResult.getDriverId());
                imageUrl = Constant.apiUrl.concat(imageUrl);
                new DownloadImageTask(imageOfDriver).execute(imageUrl);
            }

            setImageOfCard(driverSearchResult, imageOfCard);


            if (nameOfDriver != null) {
                nameOfDriver.setText(driverSearchResult.getDriverName());
            }

            if (numberOfRatings != null) {
                String text = getContext().getResources().getString(R.string.numberOfRatings);
                text = String.format(text, driverSearchResult.getTotalReviews());
                numberOfRatings.setText(text);
            }

            if(linearLayout != null){
                moveToRateDriverPage(driverSearchResult, linearLayout);
            }

        }
        return newView;
    }

    private void moveToRateDriverPage(DriverSearchResult driverSearchResult, LinearLayout linearLayout){
        BaseActivity baseActivity = Util.as(BaseActivity.class, getContext());
        linearLayout.setOnClickListener(new DriverSearchResultOnClickListener(driverSearchResult, baseActivity));
    }

    public void moveToDriverDetailsPage(DriverSearchResult driverSearchResult) {
        Intent intent = new Intent(getContext(), DriverDetails.class);
        intent.putExtra(Constant.driverId, driverSearchResult.getDriverId());
        getContext().startActivity(intent);
    }


    private void setImageOfCard(DriverSearchResult driverSearchResult, ImageView imageOfCard ){
        if(driverSearchResult.getRatingColor() == 0) {
            imageOfCard.setImageDrawable(getContext().getResources().getDrawable(R.drawable.red_circle));
        }else if(driverSearchResult.getRatingColor() == 1){
            imageOfCard.setImageDrawable(getContext().getResources().getDrawable(R.drawable.orange_circle));
        }else if(driverSearchResult.getRatingColor() == 2){
            imageOfCard.setImageDrawable(getContext().getResources().getDrawable(R.drawable.green_circle));
        }
    }
}
