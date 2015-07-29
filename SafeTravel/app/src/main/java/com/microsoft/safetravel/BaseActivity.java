package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.microsoft.safetravel.AsyncTask.GetAllReviewsOfDriver;
import com.microsoft.safetravel.AsyncTask.SearchDriversTask;
import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

public class BaseActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarWithAutoComplete();
    }


    private void setActionBarWithAutoComplete(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View actionBarWithAutoCompleteView = layoutInflater.inflate(R.layout.action_bar_with_autocomplete, null);
        actionBar.setCustomView(actionBarWithAutoCompleteView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    public void searchDrivers(View view){
        EditText editText = (EditText)findViewById(R.id.driver_name_car_number_plate);
        String url = String.format(Constant.getDriversList, editText.getText());
        url =Constant.apiUrl.concat(url);
        new SearchDriversTask(this, editText.getText().toString()).execute(url);
    }

}
