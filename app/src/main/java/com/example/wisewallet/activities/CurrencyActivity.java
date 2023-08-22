package com.example.wisewallet.activities;

import static android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrencyActivity extends AppCompatActivity {
    private CurrencyListAdapter currencyListAdapter;
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
    //Get UserId
    String userId = firebaseOperationsManager.getUserId(CurrencyActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        ArrayList<String> currencyList = new ArrayList<>();
        currencyList.add("AED");
        currencyList.add("AFN");
        currencyList.add("ALL");
        currencyList.add("AMD");
        currencyList.add("ANG");
        currencyList.add("AOA");
        currencyList.add("ARS");
        currencyList.add("AUD");
        currencyList.add("AWG");
        currencyList.add("AZN");
        currencyList.add("BAM");
        currencyList.add("BBD");
        currencyList.add("BDT");
        currencyList.add("BGN");
        currencyList.add("BHD");
        currencyList.add("BIF");
        currencyList.add("BMD");
        currencyList.add("BND");
        currencyList.add("BOB");
        currencyList.add("BRL");
        currencyList.add("BSD");
        currencyList.add("BTN");
        currencyList.add("BWP");
        currencyList.add("BYN");
        currencyList.add("BZD");
        currencyList.add("CAD");
        currencyList.add("CDF");
        currencyList.add("CHF");
        currencyList.add("CLP");
        currencyList.add("CNY");
        currencyList.add("COP");
        currencyList.add("CRC");
        currencyList.add("CUP");
        currencyList.add("CVE");
        currencyList.add("CZK");
        currencyList.add("DJF");
        currencyList.add("DKK");
        currencyList.add("DOP");
        currencyList.add("DZD");
        currencyList.add("EGP");
        currencyList.add("ERN");
        currencyList.add("ETB");
        currencyList.add("EUR");
        currencyList.add("FJD");
        currencyList.add("FKP");
        currencyList.add("GBP");
        currencyList.add("GEL");
        currencyList.add("GHS");
        currencyList.add("GIP");
        currencyList.add("GMD");
        currencyList.add("GNF");
        currencyList.add("GTQ");
        currencyList.add("GYD");
        currencyList.add("HKD");
        currencyList.add("HNL");
        currencyList.add("HRK");
        currencyList.add("HTG");
        currencyList.add("HUF");
        currencyList.add("IDR");
        currencyList.add("ILS");
        currencyList.add("INR");
        currencyList.add("IQD");
        currencyList.add("IRR");
        currencyList.add("ISK");
        currencyList.add("JMD");
        currencyList.add("JOD");
        currencyList.add("JPY");
        currencyList.add("KES");
        currencyList.add("KGS");
        currencyList.add("KHR");
        currencyList.add("KMF");
        currencyList.add("KPW");
        currencyList.add("KRW");
        currencyList.add("KWD");
        currencyList.add("KYD");
        currencyList.add("KZT");
        currencyList.add("LAK");
        currencyList.add("LBP");
        currencyList.add("LKR");
        currencyList.add("LRD");
        currencyList.add("LSL");
        currencyList.add("LYD");
        currencyList.add("MAD");
        currencyList.add("MDL");
        currencyList.add("MGA");
        currencyList.add("MKD");
        currencyList.add("MMK");
        currencyList.add("MNT");
        currencyList.add("MOP");
        currencyList.add("MRO");
        currencyList.add("MUR");
        currencyList.add("MVR");
        currencyList.add("MWK");
        currencyList.add("MXN");
        currencyList.add("MYR");
        currencyList.add("MZN");
        currencyList.add("NAD");
        currencyList.add("NGN");
        currencyList.add("NIO");
        currencyList.add("NOK");
        currencyList.add("NPR");
        currencyList.add("NZD");
        currencyList.add("OMR");
        currencyList.add("PAB");
        currencyList.add("PEN");
        currencyList.add("PGK");
        currencyList.add("PHP");
        currencyList.add("PKR");
        currencyList.add("PLN");
        currencyList.add("PYG");
        currencyList.add("QAR");
        currencyList.add("RON");
        currencyList.add("RSD");
        currencyList.add("RUB");
        currencyList.add("RWF");
        currencyList.add("SAR");
        currencyList.add("SBD");
        currencyList.add("SCR");
        currencyList.add("SDG");
        currencyList.add("SEK");
        currencyList.add("SGD");
        currencyList.add("SHP");
        currencyList.add("SLL");
        currencyList.add("SOS");
        currencyList.add("SRD");
        currencyList.add("SSP");
        currencyList.add("STN");
        currencyList.add("SYP");
        currencyList.add("SZL");
        currencyList.add("THB");
        currencyList.add("TJS");
        currencyList.add("TMT");
        currencyList.add("TND");
        currencyList.add("TOP");
        currencyList.add("TRY");
        currencyList.add("TTD");
        currencyList.add("TWD");
        currencyList.add("TZS");
        currencyList.add("UAH");
        currencyList.add("UGX");
        currencyList.add("USD");
        currencyList.add("UYU");
        currencyList.add("UZS");
        currencyList.add("VES");
        currencyList.add("VND");
        currencyList.add("VUV");
        currencyList.add("WST");
        currencyList.add("XAF");
        currencyList.add("XCD");
        currencyList.add("XOF");
        currencyList.add("XPF");
        currencyList.add("YER");
        currencyList.add("ZAR");
        currencyList.add("ZMW");
        currencyList.add("ZWL");



        Button saveButton = findViewById(R.id.next_button_currency);
        saveButton.setOnClickListener(saveButtonCurrencyListener);

        ListView currencyListView = findViewById(R.id.currency_list);
        currencyListAdapter = new CurrencyListAdapter(this, R.layout.item_currency, currencyList);
        currencyListView.setAdapter(currencyListAdapter);

        // Highlights item when pressed
        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currencyListAdapter.selectItem(position);
            }
        });
    }

    private final View.OnClickListener saveButtonCurrencyListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selectedCurrency = currencyListAdapter.getSelectedItem();
                    if (selectedCurrency != null) {
                        // Save the selected currency to Firebase or perform any other action.
                        Map<String, Object> currencyMap = new HashMap<>();
                        currencyMap.put("currency", selectedCurrency);
                        DocumentReference currencyReference = FirebaseFirestore.getInstance().collection("users").document(userId);

                        firebaseOperationsManager.submitToFirebase(CurrencyActivity.this, currencyReference, currencyMap, new FirebaseOperationsManager.FirebaseSubmitCallback() {
                            @Override
                            public void onSubmitSuccess() {
                                Intent intent = new Intent(CurrencyActivity.this, AssetsActivity.class);
                                intent.setFlags(FLAG_ACTIVITY_NEW_DOCUMENT);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onSubmitFailure(String errorMessage) {}
                        });
                    }
                    else{
                        Toast.makeText(CurrencyActivity.this, "Please Choose A Currency", Toast.LENGTH_SHORT).show();
                    }
                }
            };
}
