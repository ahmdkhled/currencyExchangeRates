package com.ahmedkhaled.currencyexchange.network;

import retrofit2.Retrofit;

/**
 * Created by Ahmed Khaled on 7/24/2018.
 */

public class RetrofitClient {
    private static Retrofit retrofit;
    private static CurrencyApiService currencyApiService;

    public static Retrofit getInstance(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl("https://exchangeratesapi.io/api/").build();
        }
        return retrofit;
    }


    public static CurrencyApiService getApiService(){
        if (currencyApiService==null){
            currencyApiService=getInstance().create(CurrencyApiService.class);
        }
        return currencyApiService;
    }
}
