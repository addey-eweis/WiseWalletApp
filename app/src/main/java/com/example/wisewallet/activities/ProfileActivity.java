package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.authentication.IsAuthenticated;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = userAuth.getCurrentUser();

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
        setContentView(R.layout.activity_profile);

//      Budget Listener
        RelativeLayout budgetButton = findViewById(R.id.budget_layout);
        budgetButton.setOnClickListener(budgetListener);



        TextView profileName = findViewById(R.id.profile_profileName);
        TextView userName = findViewById(R.id.profile_name_changeable);
        profileName.setText(firebaseUser.getDisplayName());
        userName.setText(firebaseUser.getDisplayName());

        TextView email = findViewById(R.id.profile_email_changeable);
        email.setText(Objects.requireNonNull(userAuth.getCurrentUser()).getEmail());

        Button logOutButton = findViewById(R.id.log_out_button);
        logOutButton.setOnClickListener(logOutListener);


        //Password Reset
        TextView resetQuestion = findViewById(R.id.updatePassword);
        resetQuestion.setOnClickListener(resetPasswordListener);

    }

    private final View.OnClickListener resetPasswordListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAuth.sendPasswordResetEmail(firebaseUser.getEmail())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Reset Link Sent", Toast.LENGTH_LONG).show();
                                        userAuth.signOut();
                                        if(userAuth.getCurrentUser() == null){
                                            Intent logoutIntent = new Intent(getApplicationContext(), SignActivity.class);
                                            startActivity(logoutIntent);
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            };




private final View.OnClickListener budgetListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, BudgetActivity.class);
                startActivity(intent);

            }
        };

    private final View.OnClickListener logOutListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAuth.signOut();
                if(userAuth.getCurrentUser() == null){
                    Intent logoutIntent = new Intent(getApplicationContext(), SignActivity.class);
                    startActivity(logoutIntent);
                }

            }
        };







}

