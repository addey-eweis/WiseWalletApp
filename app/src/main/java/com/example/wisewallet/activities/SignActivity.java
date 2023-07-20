package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.authentication.LogInActivity;
import com.example.wisewallet.authentication.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_WiseWallet);
        setContentView(R.layout.activity_sign_page);
        firebaseAuth = FirebaseAuth.getInstance();


        TextView loginTextView = findViewById(R.id.log_in_link);
        loginTextView.setOnClickListener(LogInListener);

        Button signUpButton = findViewById(R.id.sign_page_signup_button);
        signUpButton.setOnClickListener(signUpListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Checking if user is signed in
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            Intent authenticatedIntent = new Intent(this, MainActivity.class);
            startActivity(authenticatedIntent);
        }
    }

    private final OnClickListener LogInListener =
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();

                }
            };

    private final OnClickListener signUpListener =
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameText = ((EditText) findViewById(R.id.sign_page_name)).getText().toString();
                    Bundle bundle = new Bundle();
                    if(!nameText.equals("")){
                        bundle.putString("signup_name", nameText);
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            };
}