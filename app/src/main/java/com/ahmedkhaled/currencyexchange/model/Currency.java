package com.ahmedkhaled.currencyexchange.model;

/**
 * Created by Ahmed Khaled on 4/23/2017.
 */

public class Currency {

    private String currency;
    private double rate;

    public Currency(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }


    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }
}
