package com.example.wisewallet.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Get Login Info
        Button signInButton = findViewById(R.id.sign_page_sign_in_button);

        signInButton.setOnClickListener(signInButtonListener);


    }


    final View.OnClickListener signInButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = ((EditText) findViewById(R.id.sign_in_email)).getText().toString();
                    String password = ((EditText) findViewById(R.id.sign_in_password)).getText().toString();
                    TextView statusText = findViewById(R.id.auth_status_text);
                    AuthenticateUser authenticateUser = new AuthenticateUser(LogInActivity.this, statusText, email, password);

                    if(!email.equals("") | !password.equals("")){
                        authenticateUser.authenticateUser();
                    }
                    else{
                        authenticateUser.throwException();
                    }
                }
            };
}