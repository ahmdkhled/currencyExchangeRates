package com.ahmedkhaled.currencyexchange.network;

/**
 * Created by Ahmed Khaled on 7/22/2018.
 */

public class Urls {
    public static final String latestUrl="https://exchangeratesapi.io/api/latest";

    public static String getRateByBase(String base){
        return "https://exchangeratesapi.io/api/latest?base="+base;
    }
    public static String getRateByBaseAndSymbols(String base,String symbol){
        return "https://exchangeratesapi.io/api/latest?base="+base+"&symbols="+symbol;
    }
}
