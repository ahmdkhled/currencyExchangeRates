package com.ahmedkhaled.currencyexchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CurrencyAdapter extends BaseAdapter {

    private ArrayList<Currency> currencies;
    private Context context;

    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        this.context = context;
        this.currencies = currencies;
    }

    @Override
    public int getCount() {
        return (currencies != null) ? currencies.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return currencies.get(i).getCurrency();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // reuse of created view for smoother scrolling
        // create view if and only if view is null
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_row, null);

        TextView currency = (TextView) view.findViewById(R.id.currency);
        TextView rate = (TextView) view.findViewById(R.id.rate);
        ImageView flag = (ImageView) view.findViewById(R.id.flag);

        currency.setText(currencies.get(i).getCurrency());
        rate.setText(String.valueOf(currencies.get(i).getRate()));
        flag.setImageResource(R.drawable.dollar);

        return view;
    }
}
