package com.ahmedkhaled.currencyexchange.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmedkhaled.currencyexchange.CurrencyAdapter;
import com.ahmedkhaled.currencyexchange.R;
import com.ahmedkhaled.currencyexchange.model.Curency;
import com.ahmedkhaled.currencyexchange.network.Internet;
import com.ahmedkhaled.currencyexchange.network.RequestHandler;
import com.ahmedkhaled.currencyexchange.network.Urls;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RequestHandler.OnDataReceivedListener {
    String mUrl="https://api.fixer.io/latest";
    int baseIndex,currencyIndex;
    ArrayList<Curency> arrayList;
    String[] baseArray,currencyarray;
    TextView lastUpdated;
    RecyclerView recyclerView;
    Spinner baseSpinner,currencySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);

        lastUpdated= findViewById(R.id.lastUpdated);
        baseSpinner= findViewById(R.id.baseSpinner);
        currencySpinner= findViewById(R.id.currencySpinner);

        arrayList=new ArrayList<>();

        baseArray=new String[]{	"EUR","USD","AUD","BGN","BRL","CAD","CHF","CNY","CZK","DKK","GBP","HKD","HRK","HUF","IDR","ILS","INR",
                                "JPY","KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","ZAR",};

        currencyarray=new String[]{	"All","EUR","USD","AUD","BGN","BRL","CAD","CHF","CNY","CZK","DKK","GBP","HKD","HRK","HUF","IDR"
                ,"ILS","INR", "JPY","KRW","MXN","MYR","NOK","NZD","PHP","PLN","RON","RUB","SEK","SGD","THB","TRY","ZAR",};

        final RequestHandler.OnDataReceivedListener onDataReceivedListener=this;

        if (!Internet.isAvailable(this)){
                    Toast.makeText(getApplicationContext(),"there is no connnection ",Toast.LENGTH_LONG).show();}

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                baseIndex=i;
                String current=baseArray[i];
                if (currencyIndex==0){
                    mUrl= Urls.getRateByBase(current);
                    RequestHandler requestHandler=new RequestHandler(mUrl,onDataReceivedListener,MainActivity.this);
                    requestHandler.start();
                    Log.d("TAG2","index is 0 "+mUrl);
                }else  {
                    String currentBase=baseArray[baseIndex];
                    String currentCurrency=currencyarray[currencyIndex];
                    mUrl=Urls.getRateByBaseAndSymbols(currentBase,currentCurrency);
                    RequestHandler requestHandler=new RequestHandler(mUrl,onDataReceivedListener,MainActivity.this);
                    requestHandler.start();
                    Log.d("TAG2","index is great "+mUrl);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencyIndex=i;
                String currentBase=baseArray[baseIndex];
                String currentCurrency=currencyarray[currencyIndex];

                if (currencyIndex > 0) {
                    mUrl=Urls.getRateByBaseAndSymbols(currentBase,currentCurrency);
                    RequestHandler requestHandler=new RequestHandler(mUrl,onDataReceivedListener,MainActivity.this);
                    requestHandler.start();
                    Log.d("TAG2","second spinner "+mUrl);
                }else {
                    mUrl= Urls.getRateByBase(currentBase);
                    RequestHandler requestHandler=new RequestHandler(mUrl,onDataReceivedListener,MainActivity.this);
                    requestHandler.start();
                    Log.d("TAG2","second spinner "+mUrl);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ArrayAdapter BaseAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,baseArray);
        BaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseSpinner.setAdapter(BaseAdapter);

        ArrayAdapter currencyAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,currencyarray);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);

    }


    @Override
    public void OnDataReceived(ArrayList<Curency> currencyArray,String base,String date) {
        lastUpdated.setText("last updated : "+date);
        CurrencyAdapter currenciesAdapter=new CurrencyAdapter(this,currencyArray);
        recyclerView.setAdapter(currenciesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}
