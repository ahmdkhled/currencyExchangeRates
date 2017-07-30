package com.ahmedkhaled.currencyexchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDataReceivedListener {
    static String mUrl = "https://api.fixer.io/latest";
    int baseIndex, currencyIndex;
    ArrayList<Currency> arrayList;
    String[] baseArray, currencyArray;
    TextView lastUpdated;
    ListView list;
    Spinner baseSpinner, currencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);

        lastUpdated = (TextView) findViewById(R.id.lastUpdated);
        baseSpinner = (Spinner) findViewById(R.id.baseSpinner);
        currencySpinner = (Spinner) findViewById(R.id.currencySpinner);

        arrayList = new ArrayList<>();

        baseArray = new String[]{"EUR", "USD", "AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR",
                "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "ZAR",};

        currencyArray = new String[]{"All", "EUR", "USD", "AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "GBP", "HKD", "HRK", "HUF", "IDR"
                , "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "ZAR",};

        final OnDataReceivedListener onDataReceivedListener = this;

        if (!Internet.isAvailable(this)) {
            Toast.makeText(getApplicationContext(), "there is no connection available", Toast.LENGTH_LONG).show();
        }

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                baseIndex = i;
                String current = baseArray[i];
                if (currencyIndex == 0) {
                    mUrl = "https://api.fixer.io/latest?base=" + current;
                    Log.d("TAG2", "index is 0 " + mUrl);
                } else {
                    String currentBase = baseArray[baseIndex];
                    String currentCurrency = currencyArray[currencyIndex];
                    mUrl = "https://api.fixer.io/latest?base=" + currentBase + "&symbols=" + currentCurrency;
                    Log.d("TAG2", "index is great " + mUrl);
                }
                RequestHandler requestHandler = new RequestHandler(mUrl, onDataReceivedListener, MainActivity.this);
                requestHandler.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencyIndex = i;
                String currentBase = baseArray[baseIndex];
                String currentCurrency = currencyArray[currencyIndex];

                if (currencyIndex > 0) {
                    mUrl = "http://api.fixer.io/latest?base=" + currentBase + "&symbols=" + currentCurrency;
                    Log.d("TAG2", "second spinner " + mUrl);
                } else {
                    mUrl = "http://api.fixer.io/latest?base=" + currentBase;
                    Log.d("TAG2", "second spinner " + mUrl);
                }
                RequestHandler requestHandler = new RequestHandler(mUrl, onDataReceivedListener, MainActivity.this);
                requestHandler.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter BaseAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, baseArray);
        BaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseSpinner.setAdapter(BaseAdapter);

        ArrayAdapter currencyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyArray);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);


    }


    @Override
    public void OnDataReceived(ArrayList<Currency> currencyArray, String base, String date) {
        lastUpdated.setText(getString(R.string.last_updated) + date);
        CurrencyAdapter currenciesAdapter = new CurrencyAdapter(this, currencyArray);
        list.setAdapter(currenciesAdapter);

    }


}
