package com.example.wisewallet.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wisewallet.R;
import com.example.wisewallet.TransactionHandeling.Transaction;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class NavAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String transactionType;
    EditText date;
    String categoryName;
    String Currency;
    Boolean transactionTypeSelected = false;
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Check Authentication Status
        firebaseOperationsManager.checkAuthentication(NavAddActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_add);

        firebaseOperationsManager.getCurrency(NavAddActivity.this, new FirebaseOperationsManager.FirebaseCurrencyCallback() {
            @Override
            public void onCurrencyRead(String currency) {
                Currency = currency;
                TextView currencyTextView = findViewById(R.id.currency_display_add);
                currencyTextView.setText(currency);
            }
        });

        MaterialButton incomeButton = findViewById(R.id.income_button_type);
        MaterialButton expensesButton = findViewById(R.id.expenses_button);
        MaterialButton doneButton = findViewById(R.id.done_button_add);

        incomeButton.setOnClickListener(incomeButtonListener);
        expensesButton.setOnClickListener(expensesButtonListener);
        doneButton.setOnClickListener(doneButtonListener);

        date = (EditText)findViewById(R.id.transaction_date_input);
        date.addTextChangedListener(tw);

        Spinner spinner = findViewById(R.id.transaction_catagory_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoryName = adapterView.getItemAtPosition(i).toString();
    }

    private final View.OnClickListener doneButtonListener =
            view -> {
                EditText name = findViewById(R.id.transaction_name_input);
                EditText amount = findViewById(R.id.transaction_amount_input);
                EditText date = findViewById(R.id.transaction_date_input);
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Context context = getApplicationContext();


                Transaction transaction = new Transaction(userId, transactionType, name.getText().toString().toLowerCase(), amount.getText().toString(), date.getText().toString(), categoryName.toLowerCase(), context);
                String transactionID = transaction.firebaseTransactionSubmission();

                Toast.makeText(context, transactionID, Toast.LENGTH_SHORT).show();

                finish();
            };

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    TextWatcher tw = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private final Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8) {
                    clean = clean + ddmmyyyy.substring(clean.length());
                } else {
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day = Integer.parseInt(clean.substring(0, 2));
                    int mon = Integer.parseInt(clean.substring(2, 4));
                    int year = Integer.parseInt(clean.substring(4, 8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon - 1);
                    year = (year < 2000) ? 2000 : (year > 2023) ? 2023 : year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                    clean = String.format("%02d%02d%02d", day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                date.setText(current);
                date.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private final View.OnClickListener incomeButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.app_primary);
                    int disabledColor = ContextCompat.getColor(getApplicationContext(), R.color.app_foreground);
                    MaterialButton incomeButton = findViewById(R.id.income_button_type);
                    MaterialButton expensesButton = findViewById(R.id.expenses_button);

                    incomeButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
                    expensesButton.setBackgroundTintList(ColorStateList.valueOf(disabledColor));


                    transactionType = "Income";
                }
            };

    private final View.OnClickListener expensesButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int primaryColor = ContextCompat.getColor(getApplicationContext(), R.color.app_primary);
                    int disabledColor = ContextCompat.getColor(getApplicationContext(), R.color.app_foreground);
                    MaterialButton incomeButton = findViewById(R.id.income_button_type);
                    MaterialButton expensesButton = findViewById(R.id.expenses_button);

                    expensesButton.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
                    incomeButton.setBackgroundTintList(ColorStateList.valueOf(disabledColor));

                    transactionType = "Expense";
                }
            };








}