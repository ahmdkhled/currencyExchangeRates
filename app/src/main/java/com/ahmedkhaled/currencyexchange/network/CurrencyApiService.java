package com.ahmedkhaled.currencyexchange.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ahmed Khaled on 7/24/2018.
 */

public interface CurrencyApiService  {
    @GET("latest")
    Call<ResponseBody> getLatestRates(@Query("base") String base);
}
