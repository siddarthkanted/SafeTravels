package com.microsoft.safetravel;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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

}
