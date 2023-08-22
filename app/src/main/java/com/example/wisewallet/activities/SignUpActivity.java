package com.example.wisewallet.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;

public class SignUpActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = ((EditText)findViewById(R.id.name_signup));
        email = ((EditText)findViewById(R.id.email_signup));
        password = ((EditText)findViewById(R.id.password_signup));

        TextView nameText = findViewById(R.id.sign_up_name);

        Intent intent = getIntent();
        Bundle nameBundle = intent.getExtras();

        if (nameBundle != null) {
            nameText.setText("Welcome " + nameBundle.getString("signup_name") + "!");
            name.setText(nameBundle.getString("signup_name"));
        }


        Button SignUpButton = findViewById(R.id.sign_up_page_button);
        SignUpButton.setOnClickListener(signUpButtonListener);

    }

    private final View.OnClickListener signUpButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
                    firebaseOperationsManager.registerUser(SignUpActivity.this, name.getText().toString(), email.getText().toString(), password.getText().toString());

                }
            };


}