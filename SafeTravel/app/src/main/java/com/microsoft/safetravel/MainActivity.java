package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.safetravel.Adapter.DriverSearchResultAdapter;
import com.microsoft.safetravel.Adapter.ListOfDriversReviewAdapter;
import com.microsoft.safetravel.AsyncTask.ParseAllSmsTask;
import com.microsoft.safetravel.AsyncTask.SearchDriversTask;
import com.microsoft.safetravel.Modal.DriverReview;
import com.microsoft.safetravel.Modal.DriverSearchResult;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends Activity {

    private String reviewerId = Constant.defaultReviewerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        new ParseAllSmsTask(this, getReviewerId()).execute();
    }

    public void searchDriversOnClick(View view){
        Toast.makeText(this, "Search Button clicked", Toast.LENGTH_LONG).show();
        EditText editText = (EditText)findViewById(R.id.driver_name_car_number_plate);
        String url = String.format(Constant.getDriversList, editText.getText());
        url =Constant.apiUrl.concat(url);
        new SearchDriversTask(this, editText.getText().toString()).execute(url);
    }

    public void setActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkOrange)));
    }

    public String getReviewerId(){
        Intent intent = getIntent();
        if (null != intent) {
            reviewerId = Util.getString(reviewerId, intent.getStringExtra(Constant.reviewerId));
        }
        return reviewerId;
    }

    public void updateDriverNameCarNumberPlateTextView(){
        String driverName = "";
        Intent intent = getIntent();
        if (null != intent) {
            driverName = intent.getStringExtra(Constant.driverName);
            EditText driver_name_car_number_plate = (EditText)findViewById(R.id.driver_name_car_number_plate);
            if(null != driverName && "" != driverName)
            driver_name_car_number_plate.setText(driverName);
        }

    }

    private String getDriversSearchResult(){
        String driversSearchResult = "";
        Intent intent = getIntent();
        if (null != intent) {
            driversSearchResult = Util.getString(driversSearchResult, intent.getStringExtra(Constant.driversSearchResult));
        }
        return driversSearchResult;
    }

    public void updateDriversList(String result){
        if (null != result && "" != result.trim()) {
            try {
                Toast.makeText(this, getResources().getString(R.string.driversSearchResultObtained), Toast.LENGTH_LONG).show();
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() != 0){
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<DriverSearchResult>>() {}.getType();
                    List<DriverSearchResult> driverSearchResultList = gson.fromJson(jsonArray.toString(), listType);
                    DriverSearchResultAdapter driverSearchResultAdapter = new DriverSearchResultAdapter(this, R.layout.list_of_search_item, driverSearchResultList);
                    ListView listView = (ListView)findViewById(R.id.driversSearchResultList);
                    listView.setAdapter(driverSearchResultAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, getResources().getString(R.string.noSearchResultsFound), Toast.LENGTH_LONG).show();
        }

    }
}
