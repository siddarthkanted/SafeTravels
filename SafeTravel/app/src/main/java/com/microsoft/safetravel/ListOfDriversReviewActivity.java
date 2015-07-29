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
import com.microsoft.safetravel.AsyncTask.GetAllReviewsOfDriver;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ListOfDriversReviewActivity extends Activity {

    private String driverId = Constant.defaultDriverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_drivers_review);
        setActionBar();
        getAllReviewsOfDriver();
    }

    public void setActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkOrange)));
    }

    public void getAllReviewsOfDriver(){
        Intent intent = getIntent();
        if (null != intent) {
            driverId = Util.getString(driverId, intent.getStringExtra(Constant.driverId));
        }
        String url = String.format(Constant.getAllReviewsOfDriver,driverId);
        url = Constant.apiUrl.concat(url);
        new GetAllReviewsOfDriver(this).execute(url);
    }

    public void updateList(String listOfAllReviewsOfDriver){
        try {
            JSONArray jsonArray = new JSONArray(listOfAllReviewsOfDriver);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<DriverReview>>() {}.getType();
            List<DriverReview> driverReviewList = new Gson().fromJson(jsonArray.toString(), listType);
            Toast.makeText(this, getResources().getString(R.string.driversReviewListUpdatedSuccessfully), Toast.LENGTH_LONG).show();
            ListOfDriversReviewAdapter listOfDriversReviewAdapter =new ListOfDriversReviewAdapter(this,R.layout.list_of_drivers_review, driverReviewList);
            ListView listView = (ListView)findViewById(R.id.listOfDriversReview);
            listView.setAdapter(listOfDriversReviewAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
