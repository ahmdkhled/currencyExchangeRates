package com.ahmedkhaled.currencyexchange;

/**
 * Created by Ahmed Khaled on 4/23/2017.
 */

public class Curency {

    int flag;
    String currency;
    double rate;

    public Curency(String currency, double rate, int flag) {
        this.currency = currency;
        this.flag = flag;
        this.rate = rate;
    }
}
