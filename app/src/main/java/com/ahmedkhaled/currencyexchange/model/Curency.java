package com.ahmedkhaled.currencyexchange.model;

/**
 * Created by Ahmed Khaled on 4/23/2017.
 */

public class Curency {

    private int flag;
    private String currency;
    private double rate;

    public Curency(String currency, double rate, int flag) {
        this.currency = currency;
        this.flag = flag;
        this.rate = rate;
    }

    public int getFlag() {
        return flag;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }
}
