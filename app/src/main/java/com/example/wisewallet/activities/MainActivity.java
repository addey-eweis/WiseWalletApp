package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.example.wisewallet.fragments.AnalyticsFragment;
import com.example.wisewallet.fragments.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = new Fragment();
    private ListenerRegistration totalIncomeListenerRegistration;
    boolean isIncome = false;
    private boolean showTotal = false;
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
    //Get UserId
    String userId = firebaseOperationsManager.getUserId(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Check Authentication Status
        FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
        firebaseOperationsManager.checkAuthentication(MainActivity.this);

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_WiseWallet);
        setContentView(R.layout.activity_main);

        // Call the method to set up the listener and show the values initially
        setupAndShowValues();

//      Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new RecentFragment()).commit();

//      Profile Button
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profileButtonListener);

//      Button
        Button toggleBalanceButton = findViewById(R.id.toggleButton);
        toggleBalanceButton.setOnClickListener(toggleButtonListener);

        firebaseOperationsManager.getCurrency(MainActivity.this, new FirebaseOperationsManager.FirebaseCurrencyCallback() {
            @Override
            public void onCurrencyRead(String currency) {
                TextView currencyField = findViewById(R.id.currency_landing);
                currencyField.setText(currency);
            }
        });
    }





    // Toggle Balance Button
    private final View.OnClickListener toggleButtonListener = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            // Call the extracted method to set up the listener and update the UI
            setupAndShowValues();
        }
    };

    // Method to set up the listener and update the UI
    private void setupAndShowValues() {
        DocumentReference totalDocument = FirebaseFirestore.getInstance().collection("users").document(userId);

        TextView balance = findViewById(R.id.balance_landing);
        Button button = findViewById(R.id.toggleButton);

        totalIncomeListenerRegistration = totalDocument.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle any errors
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    String totalIncome = documentSnapshot.getString("totalIncome");
                    String totalExpenses = documentSnapshot.getString("totalExpenses");
                    String total;
                    if (!isIncome) {
                        total = totalIncome;
                        button.setText("Inflows");
                        isIncome = true;
                    }
                    else{
                        total = totalExpenses;
                        button.setText("Outflows");
                        isIncome = false;
                    }

                    if(total != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                balance.setText(total);
                            }
                        });
                    }
                }
            }
        });
    }



    private final View.OnClickListener profileButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(profileIntent);
                }
            };



    private final NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int fragItem = item.getItemId();

                    switch (fragItem){
                        case R.id.nav_recent:
                            selectedFragment = new RecentFragment();
                            break;
                        case R.id.nav_analytics:
                            selectedFragment = new AnalyticsFragment();
                            break;
                        case R.id.nav_add:
                            Intent addIntent = new Intent(getApplicationContext(), NavAddActivity.class);
                            startActivity(addIntent);
                            return false;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();
                    return true;
                }
            };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (totalIncomeListenerRegistration != null) {
            totalIncomeListenerRegistration.remove();
        }
    }

}