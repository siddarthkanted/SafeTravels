package com.microsoft.safetravel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.safetravel.AsyncTask.DownloadImageTask;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.R;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import java.util.List;

/**
 * Created by sikanted on 7/27/2015.
 */
public class ListOfDriversReviewAdapter extends ArrayAdapter<DriverReview> {

    public ListOfDriversReviewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListOfDriversReviewAdapter(Context context, int resource, List<DriverReview> items) {
        super(context, resource, items);
    }

    /*
        <ImageView
            android:id="@+id/imageOfReviewer"
                android:id="@+id/nameOfMember"
                android:id="@+id/dateOfReview"
                 android:id="@+id/reviewText"
    */
    // getView method is called for each item of ListView
    public View getView(int position,  View view, ViewGroup parent)
    {
        View newView = view;

        if (newView == null) {
            LayoutInflater layoutInflater= LayoutInflater.from(getContext());
            newView = layoutInflater.inflate(R.layout.list_of_drivers_review, null);
        }
        DriverReview driverReview = (DriverReview)getItem(position);

        if (driverReview != null) {
            ImageView imageOfReviewer = (ImageView)newView.findViewById(R.id.imageOfReviewer);
            TextView nameOfMember = (TextView)newView.findViewById(R.id.nameOfMember);
            TextView dateOfReview = (TextView)newView.findViewById(R.id.dateOfReview);
            TextView reviewText = (TextView)newView.findViewById(R.id.reviewText);

            if(imageOfReviewer != null) {
                String imageUrl = String.format(Constant.imageOfReviewer, driverReview.getReviewerId());
                imageUrl = Constant.apiUrl.concat(imageUrl);
                new DownloadImageTask(imageOfReviewer).execute(imageUrl);
            }

            if (nameOfMember != null) {
                nameOfMember.setText(driverReview.getReviewerId());
            }

            if (dateOfReview != null) {
                dateOfReview.setText(Util.convertUtcToDisplayTimeFormat(driverReview.getReviewTime()));
            }

            if (reviewText != null) {
                reviewText.setText(driverReview.getReviewComment());
            }
        }
        return newView;
    }

}
