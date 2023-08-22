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
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;


public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
        if(firebaseOperationsManager.getUser() != null){
            Intent authenticatedIntent = new Intent(this, MainActivity.class);
            authenticatedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(authenticatedIntent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_WiseWallet);
        setContentView(R.layout.activity_sign_page);

        TextView loginTextView = findViewById(R.id.log_in_link);
        loginTextView.setOnClickListener(LogInListener);

        Button signUpButton = findViewById(R.id.sign_page_signup_button);
        signUpButton.setOnClickListener(signUpListener);
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
                    if(!nameText.equals("") | nameText != null){
                        Bundle bundle = new Bundle();
                        bundle.putString("signup_name", nameText);
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            };
}