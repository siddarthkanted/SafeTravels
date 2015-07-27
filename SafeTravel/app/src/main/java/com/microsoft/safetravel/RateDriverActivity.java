package com.microsoft.safetravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.microsoft.safetravel.AsyncTask.AddReview;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.HttpUtil;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;


public class RateDriverActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);
    }

    public void submitReview(View view) {
        RadioButton fastDrivingRadioButton = (RadioButton)Util.getSelectedRadioButton((RadioGroup)findViewById(R.id.fastDrivingRadioGroup));
        RadioButton behaviourRadioButton = (RadioButton)Util.getSelectedRadioButton((RadioGroup)findViewById(R.id.behaviourRadioGroup));
        RatingBar conditionOfCarRatingBar = (RatingBar)findViewById(R.id.conditionOfCarRatingBar);
        RadioButton recommendDriverRadioButton = (RadioButton)Util.getSelectedRadioButton((RadioGroup)findViewById(R.id.recommendDriverRadioGroup));
        EditText aditionalDetailsEditText = (EditText)findViewById(R.id.aditionalDetailsEditText);
        int drivingSpeedRating = Integer.parseInt(fastDrivingRadioButton.getText().toString());
        int driverBehaviorRating = Integer.parseInt(behaviourRadioButton.getText().toString());
        int vehicleConditionRating = (int)(Math.round(conditionOfCarRatingBar.getRating()));
        boolean driverRecommendation = Boolean.parseBoolean(recommendDriverRadioButton.getText().toString());
        String reviewComment = aditionalDetailsEditText.getText().toString();
        Intent intent = getIntent();
        String reviewerId = "", driverId="", tripId="";
        if (null != intent) {
            reviewerId = null != intent.getStringExtra(Constant.reviewerId)? intent.getStringExtra(Constant.reviewerId): UUID.randomUUID().toString();
            driverId = null != intent.getStringExtra(Constant.driverId)? intent.getStringExtra(Constant.driverId): UUID.randomUUID().toString();
            tripId = null != intent.getStringExtra(Constant.tripId)? intent.getStringExtra(Constant.tripId): UUID.randomUUID().toString();
        }
        JSONObject jsonObject = createJson(reviewerId, driverId, tripId, drivingSpeedRating,driverBehaviorRating,vehicleConditionRating, driverRecommendation,reviewComment);
        String url = Constant.apiUrl.concat(Constant.addReviewUrlPath);
        new AddReview(this).execute(url, jsonObject.toString());
    }

    /*
    {
    "reviewerId": "reviewer1",
    "driverId": "driver1",
    "tripId": "trip1",
    "drivingSpeedRating": 1,
    "driverBehaviorRating": 2,
    "vehicleConditionRating": 3,
    "driverRecommendation": true,
    "reviewComment": "this is comment1"
    }
     */
    public JSONObject createJson(String reviewerId, String driverId, String tripId, int drivingSpeedRating, int driverBehaviorRating,int vehicleConditionRating, boolean driverRecommendation, String reviewComment){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.reviewerId,reviewerId);
            jsonObject.put(Constant.driverId, driverId);
            jsonObject.put(Constant.tripId, tripId);
            jsonObject.put(Constant.drivingSpeedRating,drivingSpeedRating);
            jsonObject.put(Constant.driverBehaviorRating, driverBehaviorRating);
            jsonObject.put(Constant.vehicleConditionRating, vehicleConditionRating);
            jsonObject.put(Constant.driverRecommendation, driverRecommendation);
            jsonObject.put(Constant.reviewComment, reviewComment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
