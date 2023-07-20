package com.example.wisewallet.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        TextView nameText = findViewById(R.id.sign_up_name);
        Intent intent = getIntent();
        Bundle nameBundle = intent.getExtras();
        nameText.setText("Welcome " + nameBundle.getString("signup_name") + "!");

        EditText signupName = findViewById(R.id.name_signup);
        signupName.setText(nameBundle.getString("signup_name"));

        Button SignUpButton = findViewById(R.id.sign_up_page_button);
        SignUpButton.setOnClickListener(signUpButtonListener);


    }

    private final View.OnClickListener signUpButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = ((EditText)findViewById(R.id.name_signup)).getText().toString();
                    String email = ((EditText)findViewById(R.id.email_signup)).getText().toString();
                    String password = ((EditText)findViewById(R.id.password_signup)).getText().toString();

                    RegisterUser userSignup = new RegisterUser(SignUpActivity.this, name, email, password);

                    if(!name.equals("") | !email.equals("") | !password.equals("")){
                        userSignup.registration();
                    }
                    else{
                        userSignup.throwException();
                    }
                }
            };


}