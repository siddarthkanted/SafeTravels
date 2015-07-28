package com.microsoft.safetravel.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.microsoft.safetravel.R;
import com.microsoft.safetravel.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sikanted on 7/27/2015.
 */
public class AddReview extends AsyncTask<String, Integer, String> {

    private Context context;

    public AddReview(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String url = params[0];
            String jsonString = params[1];
            return HttpUtil.SendHttpPutRequest(url, new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        if (null != result) {
            Toast.makeText(context, context.getString(R.string.reviewSubmittedSuccessfully), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, context.getString(R.string.reviewSubmittedFailure), Toast.LENGTH_LONG).show();
        }
    }


}
