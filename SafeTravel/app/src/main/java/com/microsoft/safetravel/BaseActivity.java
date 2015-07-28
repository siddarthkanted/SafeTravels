package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.microsoft.safetravel.Util.Constant;
import com.microsoft.safetravel.Util.Util;

public class BaseActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarWithAutoComplete();
        updateDriverNameCarNumberPlateTextView();
    }

    private void updateDriverNameCarNumberPlateTextView(){
        String driverId = Constant.defaultDriverId;
        Intent intent = getIntent();
        if (null != intent) {
            driverId = Util.getString(driverId, intent.getStringExtra(Constant.driverId));
        }
        EditText driver_name_car_number_plate = (EditText)findViewById(R.id.driver_name_car_number_plate);
        driver_name_car_number_plate.setText(driverId);
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

}
