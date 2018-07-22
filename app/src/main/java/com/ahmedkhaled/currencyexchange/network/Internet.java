package com.ahmedkhaled.currencyexchange.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ahmed Khaled on 7/12/2017.
 */

public class Internet {

    public static boolean isAvailable(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();


    }
}
