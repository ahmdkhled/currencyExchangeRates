package com.ahmedkhaled.currencyexchange.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedkhaled.currencyexchange.R;
import com.ahmedkhaled.currencyexchange.model.Currency;

import java.util.ArrayList;



public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder> {

    private ArrayList<Currency> currencies;
    private Context context;

    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        this.context = context;
        this.currencies = currencies;
    }

    @NonNull
    @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_row,parent,false);
        return new CurrencyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        holder.currency.setText(currencies.get(position).getCurrency());
        holder.rate.setText(String.valueOf(currencies.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        if (currencies==null)
            return 0;
        return currencies.size();
    }

    class CurrencyHolder extends RecyclerView.ViewHolder{
        TextView currency,rate;
        public CurrencyHolder(View itemView) {
            super(itemView);
            currency=itemView.findViewById(R.id.currency);
            rate=itemView.findViewById(R.id.rate);
        }


}
    }
