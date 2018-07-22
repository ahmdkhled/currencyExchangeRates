package com.ahmedkhaled.currencyexchange.network;

import android.app.Activity;
import android.util.Log;

import com.ahmedkhaled.currencyexchange.R;
import com.ahmedkhaled.currencyexchange.model.Curency;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class RequestHandler extends Thread {


    private String myUrl;
    String currencyName;
    Double rate=0.0;
    private ArrayList<Curency> currencyList;
    OnDataReceivedListener onDataReceivedListener;
    Activity activity;

    public RequestHandler(String myUrl ,OnDataReceivedListener onDataReceivedListener,Activity activity ){
        this.myUrl=myUrl;
        currencyList=new ArrayList<>();
        this.onDataReceivedListener=onDataReceivedListener;
        this.activity=activity;

    }

        @Override
        public void run() {
           // Log.d("TAG","started");
            try {
                URL url =new URL(myUrl);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String result=bufferedReader.readLine();
//                Log.d("TAG","result  "+result);

                JSONObject baseObject=new JSONObject(result);
                final String base=baseObject.getString("base");
                final String date =baseObject.getString("date");
                JSONObject ratesObject=baseObject.getJSONObject("rates");

                JSONArray ratesArray=ratesObject.names();

                for (int i = 0; i <ratesArray.length() ; i++) {

                    currencyName=(String) ratesArray.get(i);
                    rate=ratesObject.getDouble(currencyName);
                    currencyList.add(new Curency(currencyName,rate, R.drawable.dollar));
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    onDataReceivedListener.OnDataReceived(currencyList,base,date);
                    }
                });

            } catch (Exception e) {
                Log.d("TAG","error is "+e.getMessage());
            }


        }

    public interface OnDataReceivedListener{
        void OnDataReceived(ArrayList<Curency> currencyArray,String base,String date);
    }


    }





