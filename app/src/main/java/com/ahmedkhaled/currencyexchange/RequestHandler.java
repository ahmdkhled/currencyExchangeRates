package com.ahmedkhaled.currencyexchange;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


interface OnDataReceivedListener {
    void OnDataReceived(ArrayList<Currency> currencyArray, String base, String date);
}

public class RequestHandler extends Thread {


    private String myUrl;
    private String currencyName;
    private Double rate = 0.0;
    private ArrayList<Currency> currencyList;
    private OnDataReceivedListener onDataReceivedListener;
    private Activity activity;

    RequestHandler(String myUrl, OnDataReceivedListener onDataReceivedListener, Activity activity) {
        this.myUrl = myUrl;
        currencyList = new ArrayList<>();
        this.onDataReceivedListener = onDataReceivedListener;
        this.activity = activity;

    }

    @Override
    public void run() {
        try {
            URL url = new URL(myUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String result = bufferedReader.readLine();

            JSONObject baseObject = new JSONObject(result);
            final String base = baseObject.getString("base");
            final String date = baseObject.getString("date");
            JSONObject ratesObject = baseObject.getJSONObject("rates");

            JSONArray ratesArray = ratesObject.names();

            for (int i = 0; i < ratesArray.length(); i++) {

                currencyName = (String) ratesArray.get(i);
                rate = ratesObject.getDouble(currencyName);
                currencyList.add(new Currency(currencyName, rate, R.drawable.dollar));
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onDataReceivedListener.OnDataReceived(currencyList, base, date);
                }
            });

        } catch (Exception e) {
            Log.d("TAG", "error is " + e.getMessage());
        }


    }


}





