package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wisewallet.R;
import com.example.wisewallet.fragments.AnalyticsFragment;
import com.example.wisewallet.fragments.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    Fragment selectedFragment = new Fragment();

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

//        //Switch Landing
//        SwitchCompat switchCompat = findViewById(R.id.switch_landing);
//        switchCompat.setOnCheckedChangeListener(landingSwitchListener);

    }


//    CompoundButton.OnCheckedChangeListener landingSwitchListener =
//            new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    TextView moneyRemaining = findViewById(R.id.dollar_amount_landing);
//                    moneyRemaining.setText("$128.23");
//
//
//                }
//            };


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
}