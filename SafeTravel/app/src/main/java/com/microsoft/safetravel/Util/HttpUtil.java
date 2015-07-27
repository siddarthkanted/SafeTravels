package com.microsoft.safetravel.Util;

import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sikanted on 7/27/2015.
 */
public class HttpUtil {

    public static String GetResponse(HttpURLConnection httpURLConnection) {
        StringBuffer response = new StringBuffer();
        try {
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public static String SendHttpPostRequest(String webUrl, JSONObject postDataParams){
        try {
            URL url = new URL(webUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            WriteDataToStream(httpURLConnection, postDataParams);
            return GetResponse(httpURLConnection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String SendHttpPostRequest(String webUrl, HashMap<String, String> postDataParams){
        try {
            URL url = new URL(webUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            WriteDataToStream(httpURLConnection, postDataParams);
            return GetResponse(httpURLConnection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void WriteDataToStream(HttpURLConnection httpURLConnection, JSONObject postDataParams){
        try {
            httpURLConnection.setRequestProperty(Constant.Content_Type, "application/json; charset=UTF-8");
            OutputStream outputStreams = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStreams);
            outputStreams.write(postDataParams.toString().getBytes(Constant.UTF_8));
            outputStreams.close();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteDataToStream(HttpURLConnection httpURLConnection, HashMap<String, String> postDataParams){
        try {
            OutputStream outputStreams = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStreams, Constant.UTF_8));
            bufferedWriter.write(getPostDataString(postDataParams));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreams.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), Constant.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), Constant.UTF_8));
        }
        return result.toString();
    }

    public static String SendHttpGetRequest(String webUrl) {
        try {
            URL url = new URL(webUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            return GetResponse(httpURLConnection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
