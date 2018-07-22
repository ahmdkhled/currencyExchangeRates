package com.ahmedkhaled.currencyexchange;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedkhaled.currencyexchange.model.Curency;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder> {

    private ArrayList<Curency> curencies;
    private Context context;

    public CurrencyAdapter(Context context, ArrayList<Curency> curencies) {
        this.context = context;
        this.curencies = curencies;
    }

    @NonNull
    @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_row,parent,false);
        return new CurrencyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        holder.currency.setText(curencies.get(position).getCurrency());
        holder.rate.setText(String.valueOf(curencies.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return curencies.size();
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
