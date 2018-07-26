package com.ahmedkhaled.currencyexchange.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedkhaled.currencyexchange.model.Currency;
import com.ahmedkhaled.currencyexchange.adapters.CurrencyAdapter;
import com.ahmedkhaled.currencyexchange.R;
import com.ahmedkhaled.currencyexchange.network.Internet;
import com.ahmedkhaled.currencyexchange.network.JsonParser;
import com.ahmedkhaled.currencyexchange.network.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String mUrl;
    int baseIndex,currencyIndex;
    TextView lastUpdated;
    RecyclerView recyclerView;
    Spinner baseSpinner,currencySpinner;
    ArrayList<Currency> currencies;
    ArrayList<String> baseArray,currencyArray;
    ViewGroup container;
    String date;
    String base="EUR";
    boolean baseFT=true;
    boolean currencyFT=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);

        lastUpdated= findViewById(R.id.lastUpdated);
        baseSpinner= findViewById(R.id.baseSpinner);
        currencySpinner= findViewById(R.id.currencySpinner);

        lastUpdated =  findViewById(R.id.lastUpdated);
        baseSpinner = findViewById(R.id.baseSpinner);
        currencySpinner = findViewById(R.id.currencySpinner);
        container=findViewById(R.id.activity_main);
        currencyArray = new ArrayList<>();
        baseArray = new ArrayList<>();






        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      if (baseFT){
                          baseFT=false;
                      }else{
                          if (currencyIndex == 0) {
                              baseIndex = i;
                              base = baseArray.get(i);
                              loadData(base,null);
                              Log.d("TAG2", "index is 0 " + mUrl);
                          } else {
                              base = baseArray.get(i);
                              String currentCurrency = currencyArray.get(currencyIndex);
                              loadData(base,currentCurrency);
                              Log.d("TAG2", "index is great " +base);
                          }
                      }
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {
                  }

              });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (currencyFT){
                    currencyFT=false;
                }else {
                    currencyIndex = i;
                    String currentCurrency = currencyArray.get(currencyIndex);
                    if (currencyIndex > 0) {
                        ArrayList<Currency> filteredList=filterResults(currentCurrency);
                        populateData(filteredList,date);
                        Log.d("TAG2","second spinner filter");
                    }else {
                        populateData(currencies,date);
                        Log.d("TAG2","second spinner all");
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
        if (Internet.isAvailable(this)) {
            loadData(base,null);
        }else {
            showSnackbar();
        }


    }

    void loadData(final String base, final String filterCurrency){
        RetrofitClient.getApiService().getLatestRates(base)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result=response.body().string();
                            date= JsonParser.getDate(result);
                            currencies=JsonParser.parse(result);
                            Log.d("TAG2","size "+baseArray.size());

                            if (baseArray.size()==0){
                                baseArray=JsonParser.getcurrencies(result);
                                baseArray.add(0,base);
                                currencyArray.clear();
                                currencyArray.addAll(baseArray);
                                currencyArray.add(0,"all");
                                populateSpinners();
                            }
                            if (filterCurrency==null){
                            populateData(currencies,date);
                            }else{
                                populateData(filterResults(filterCurrency),date);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"error loading date ",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    void populateData(ArrayList<Currency> currencyList,String date){
        CurrencyAdapter currenciesAdapter=new CurrencyAdapter(getApplicationContext()
                ,currencyList);
        recyclerView.setAdapter(currenciesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lastUpdated.setText("last updated : "+date);
    }

    void populateSpinners(){
        ArrayAdapter BaseAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,baseArray);
        BaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseSpinner.setAdapter(BaseAdapter);

        ArrayAdapter currencyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyArray);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);
    }

    ArrayList<Currency> filterResults(String currentCurrency){
        ArrayList<Currency> filteredList=new ArrayList<>();
        for (Currency currency:currencies){
            if (currency.getCurrency().equals(currentCurrency)){
                filteredList.add(currency);
                break;
            }
        }
        return filteredList;
    }

    void showSnackbar(){
        Snackbar snackbar=Snackbar.make(container,"check your connection ",Snackbar.LENGTH_INDEFINITE)
                .setAction("retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Internet.isAvailable(getApplicationContext())){
                            loadData(base,null);
                        }else{
                            showSnackbar();
                        }
                    }
                });
        snackbar.show();
    }
}

