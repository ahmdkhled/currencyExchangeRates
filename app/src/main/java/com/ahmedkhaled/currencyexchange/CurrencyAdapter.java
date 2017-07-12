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

    private ArrayList<Curency> curencies;
    private Context context;

    public CurrencyAdapter(Context context, ArrayList<Curency> curencies) {
        this.context = context;
        this.curencies = curencies;
        curencies=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return curencies.size();
    }

    @Override
    public Object getItem(int i) {
        return curencies.get(i).currency;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View row=layoutInflater.inflate(R.layout.list_row,null);

        TextView currency= (TextView) row.findViewById(R.id.currency);
        TextView rate= (TextView) row.findViewById(R.id.rate);
        ImageView flag= (ImageView) row.findViewById(R.id.flag);

        currency.setText(curencies.get(i).currency);
        rate.setText(String.valueOf(curencies.get(i).rate));
        flag.setImageResource(R.drawable.dollar);

        return row;
    }
}
