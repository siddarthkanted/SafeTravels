package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.safetravel.Adapter.ListOfDriversReviewAdapter;
import com.microsoft.safetravel.Adapter.UnratedTripsAdapter;
import com.microsoft.safetravel.AsyncTask.GetAllUnratedTrips;
import com.microsoft.safetravel.AsyncTask.GetLastTravelledDateTask;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Modal.UnratedTrips;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;


public class UnratedTripsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unrated_trips);
        setActionBar();
        getAllUnratedTrips();
    }

    public void setActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkOrange)));
    }

    public String getReviewerId(){
        String reviewerId = "a73042ee-63bf-4aed-ac27-7eca418b860e";
        Intent intent = getIntent();
        if (null != intent) {
            reviewerId = Util.getString(reviewerId, intent.getStringExtra(Constant.reviewerId));
        }
        return reviewerId;
    }

    public void getAllUnratedTrips(){
        String reviewerId = getReviewerId();
        String url = String.format(Constant.getAllUnratedTrips,reviewerId);
        url = Constant.apiUrl.concat(url);
        new GetAllUnratedTrips(this).execute(url);
    }

    public void updateAllUnratedTrips(String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray.length()==0){
                Toast.makeText(this, getResources().getString(R.string.noRatedTripFound), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.unRatedTripFound), Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<UnratedTrips>>() {}.getType();
                List<UnratedTrips> unratedTripsList = gson.fromJson(jsonArray.toString(), listType);

                UnratedTripsAdapter listOfDriversReviewAdapter = new UnratedTripsAdapter(this, R.layout.unrated_trips_list, unratedTripsList);
                ListView listView = (ListView)findViewById(R.id.unratedTripsList);
                listView.setAdapter(listOfDriversReviewAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
