package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.safetravel.Adapter.ListOfDriversReviewAdapter;
import com.microsoft.safetravel.AsyncTask.DownloadImageTask;
import com.microsoft.safetravel.AsyncTask.GetAllReviewsOfDriver;
import com.microsoft.safetravel.AsyncTask.GetDriverProfileTask;
import com.microsoft.safetravel.AsyncTask.GetLastTravelledDateTask;
import com.microsoft.safetravel.Modal.DriverProfile;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Modal.ReviewersTravelWithDriver;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


public class DriverDetails extends Activity {

    private String driverId = Constant.defaultDriverId;
    private String reviewerId = Constant.defaultReviewerId;
    private List<ReviewersTravelWithDriver> reviewersTravelWithDriverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        setActionBar();
        getDriverProfile();
        getAllReviewsOfDriver();
        getLastTravelledDate();
    }

    public void setActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkOrange)));
    }

    //getLastTravelledDate = "api/review?driverId=%s&reviewerId=%s";
    public void getLastTravelledDate(){
        driverId = getDriverId();
        reviewerId = getReviewerId();
        String url = String.format(Constant.getLastTravelledDate,driverId, reviewerId);
        url = Constant.apiUrl.concat(url);
        new GetLastTravelledDateTask(this).execute(url);
    }

    public void updateLastTravelledDate(String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray.length()==0){
                removeLastTravelledDate();
            }else{
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ReviewersTravelWithDriver>>() {}.getType();
                reviewersTravelWithDriverList = gson.fromJson(jsonArray.toString(), listType);
                TextView notYetRated = (TextView)findViewById(R.id.notYetRated);
                notYetRated.setText(String.format(getResources().getString(R.string.notYetRated), Util.convertUtcToDisplayTimeFormat(reviewersTravelWithDriverList.get(0).getReviewTime())));
                addLastTravelledDate();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addLastTravelledDate(){
        //LinearLayout driverDetailsParent =(LinearLayout)findViewById(R.id.driverDetailsParent);
        RelativeLayout lastTravelledDate =(RelativeLayout)findViewById(R.id.lastTravelledDate);
        //driverDetailsParent.addView(lastTravelledDate);
        lastTravelledDate.setVisibility(View.VISIBLE);
    }

    public void removeLastTravelledDate(){
        //LinearLayout driverDetailsParent =(LinearLayout)findViewById(R.id.driverDetailsParent);
        RelativeLayout lastTravelledDate =(RelativeLayout)findViewById(R.id.lastTravelledDate);
        //driverDetailsParent.removeView(lastTravelledDate);
        lastTravelledDate.setVisibility(View.GONE);
    }

    public void moveToRateDriverPage(View view){
        Intent intent = new Intent(this, RateDriverActivity.class);
        intent.putExtra(Constant.driverId, getDriverId());
        intent.putExtra(Constant.tripId, reviewersTravelWithDriverList.get(0).getTripId());
        startActivity(intent);
    }

    public void getDriverProfile(){
        driverId = getDriverId();
        String url =String.format(Constant.driverProfile, driverId);
        url = Constant.apiUrl.concat(url);
        new GetDriverProfileTask(this).execute(url);
    }


    /*
    driverName
    numberOfRatings
    numberOfRecommendations
    imageOfCard
    licenseNumberValue
    validityValue
    contactNumberTextView
     */
    public void updateDriverProfile(String result){

            Gson gson = new Gson();
            DriverProfile driverProfile = gson.fromJson(result, DriverProfile.class);
        TextView  textView = (TextView)findViewById(R.id.driverName);
        textView.setText(driverProfile.getDriverName());
        textView = (TextView)findViewById(R.id.licenseNumberValue);
        textView.setText(driverProfile.getLicenseNumber());
        textView = (TextView)findViewById(R.id.validityValue);
        textView.setText(driverProfile.getLicenseExpiry());
        textView = (TextView)findViewById(R.id.contactNumberValue);
        textView.setText(driverProfile.getContactNumber());
        textView = (TextView)findViewById(R.id.policeIdNumberValue);
        textView.setText(driverProfile.getPoliceIdNumber());
        textView = (TextView)findViewById(R.id.numberOfRatings);
        textView.setText(String.format(getResources().getString(R.string.numberOfRatings), driverProfile.getTotalReviews()));
        textView = (TextView)findViewById(R.id.numberOfRecommendations);
        textView.setText(String.format(getResources().getString(R.string.numberOfRecommendations), driverProfile.getRecommendationScore()+"%"));
        setImageOfCard(driverProfile);
        setDriverImage(driverProfile);

    }

    private void setDriverImage(DriverProfile driverProfile) {
        ImageView imageView = (ImageView)findViewById(R.id.imageOfDriver);
        String imageUrl = String.format(Constant.imageOfReviewer, driverProfile.getDriverId());
        imageUrl = Constant.apiUrl.concat(imageUrl);
        new DownloadImageTask(imageView).execute(imageUrl);
    }

    //imageOfCard
    private void setImageOfCard(DriverProfile driverProfile){
        ImageView imageView = (ImageView)findViewById(R.id.imageOfCard);
            if(driverProfile.getRatingColor() == 0) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.red_circle));
            }else if(driverProfile.getRatingColor() == 1){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.orange_circle));
            }else if(driverProfile.getRatingColor() == 2){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.green_circle));
            }
    }

    public String getDriverId(){
        Intent intent = getIntent();
        if (null != intent) {
            driverId = Util.getString(driverId, intent.getStringExtra(Constant.driverId));
        }
        return driverId;
    }

    public String getReviewerId(){
        Intent intent = getIntent();
        if (null != intent) {
            reviewerId = Util.getString(reviewerId, intent.getStringExtra(Constant.reviewerId));
        }
        return reviewerId;
    }

    public void getAllReviewsOfDriver() {
        driverId = getDriverId();
        String url = String.format(Constant.getAllReviewsOfDriver, driverId);
        url = Constant.apiUrl.concat(url);
        new GetAllReviewsOfDriver(this).execute(url);
    }

    public void updateList(String listOfAllReviewsOfDriver) {
        try {
            JSONArray jsonArray = new JSONArray(listOfAllReviewsOfDriver);
            if(jsonArray.length()==0){
                Toast.makeText(this, getResources().getString(R.string.noDriverReviewFound), Toast.LENGTH_LONG).show();
                removeDriversReviewListLayout();
            }else{
                Toast.makeText(this, getResources().getString(R.string.driverReviewsFound), Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<DriverReview>>() {}.getType();
                List<DriverReview> driverReviewList = gson.fromJson(jsonArray.toString(), listType);
                Toast.makeText(this, getResources().getString(R.string.driversReviewListUpdatedSuccessfully), Toast.LENGTH_LONG).show();
                ListOfDriversReviewAdapter listOfDriversReviewAdapter = new ListOfDriversReviewAdapter(this, R.layout.list_of_drivers_review, driverReviewList);
                ListView listView = (ListView)findViewById(R.id.driversReviewList);
                listView.setAdapter(listOfDriversReviewAdapter);
                addDriversReviewListLayout();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //driversReviewListLayout
    public void removeDriversReviewListLayout(){
        //LinearLayout driverDetailsParent =(LinearLayout)findViewById(R.id.driverDetailsParent);
        RelativeLayout driversReviewListLayout =(RelativeLayout)findViewById(R.id.driversReviewListLayout);
       // driverDetailsParent.removeView(driversReviewListLayout);
        driversReviewListLayout.setVisibility(View.GONE);
    }

    public void addDriversReviewListLayout(){
        //LinearLayout driverDetailsParent =(LinearLayout)findViewById(R.id.driverDetailsParent);
        RelativeLayout driversReviewListLayout =(RelativeLayout)findViewById(R.id.driversReviewListLayout);
        //driverDetailsParent.addView(driversReviewListLayout);
        driversReviewListLayout.setVisibility(View.VISIBLE);
    }
}