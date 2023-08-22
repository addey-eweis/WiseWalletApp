package com.example.wisewallet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_password);

        Button signInButton = findViewById(R.id.sign_page_sign_in_button);
        signInButton.setOnClickListener(signInButtonListener);

    }


    final View.OnClickListener signInButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
                    firebaseOperationsManager.authenticateUser(LogInActivity.this, email.getEditText().getText().toString(), password.getEditText().getText().toString());

                }
            };
}