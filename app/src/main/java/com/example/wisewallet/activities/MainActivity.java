package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wisewallet.R;
import com.example.wisewallet.authentication.IsAuthenticated;
import com.example.wisewallet.fragments.AnalyticsFragment;
import com.example.wisewallet.fragments.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = new Fragment();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    @Override
    protected void onResume() {
        super.onResume();

        IsAuthenticated isAuthenticated = new IsAuthenticated(this);
        boolean isUserAuthenticated = isAuthenticated.checkAuthentication();

        if (!isUserAuthenticated) {
            isAuthenticated.redirectToLoginScreen();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_WiseWallet);
        setContentView(R.layout.activity_main);


//      Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new RecentFragment()).commit();

//      Profile Button
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profileButtonListener);

        Button toggleBalanceButton = findViewById(R.id.toggleButton);
        toggleBalanceButton.setOnClickListener(toggleButtonListener);
    }

    private final View.OnClickListener profileButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(profileIntent);
                }
            };

// Toggle Balance Button
    private final View.OnClickListener toggleButtonListener =
            new View.OnClickListener() {
                boolean isTotal = false;
                @Override
                public void onClick(View view) {
                        Button button = view.findViewById(R.id.toggleButton);
                        TextView balance = view.findViewById(R.id.amount_amount_landing);
                    if(isTotal){
                        button.setText("Remaining Balance");
                        isTotal = false;
                    }
                    else{
                        button.setText("Total Balance");
                        isTotal = true;

                    }

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
}