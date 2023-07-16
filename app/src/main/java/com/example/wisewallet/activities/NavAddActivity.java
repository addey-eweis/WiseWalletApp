package com.example.wisewallet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.authentication.IsAuthenticated;

public class NavAddActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_nav_add);
    }
}