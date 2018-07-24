package com.ahmedkhaled.currencyexchange.network;

import com.ahmedkhaled.currencyexchange.model.Currency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Ahmed Khaled on 7/24/2018.
 */

public class JsonParser {
    public static ArrayList<Currency> parse(String jsonString){
        ArrayList<Currency> currencies=new ArrayList<>();

        try {
            JSONObject mainObj=new JSONObject(jsonString);
            JSONObject rates=mainObj.optJSONObject("rates");
            Iterator<String> i=rates.keys();
            while (i.hasNext()){
                String currency=i.next();
                Double rate=rates.getDouble(currency);
                currencies.add(new Currency(currency,rate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currencies;

    }

    public static String getDate(String jsonString){

        try {
            JSONObject mainObj=new JSONObject(jsonString);
            return mainObj.optString("date");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
