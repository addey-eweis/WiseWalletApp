package com.example.wisewallet.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.authentication.SignActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    final FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = userAuth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//      Budget Listener
        RelativeLayout budgetButton = findViewById(R.id.budget_layout);
        budgetButton.setOnClickListener(budgetListener);

//      username copy

        TextView username = findViewById(R.id.profile_userName);
        username.setOnLongClickListener(usernameCopy);

//      Personal Info Edit
        Button editPersonalInfo = findViewById(R.id.personal_info_edit);
        editPersonalInfo.setOnClickListener(editPersonalInfoListener);

        TextView profileName = findViewById(R.id.profile_profileName);
        TextView userName = findViewById(R.id.profile_name_changeable);
        profileName.setText(firebaseUser.getDisplayName());
        userName.setText(firebaseUser.getDisplayName());

        TextView email = findViewById(R.id.profile_email_changeable);
        email.setText(Objects.requireNonNull(userAuth.getCurrentUser()).getEmail());

        Button logOutButton = findViewById(R.id.log_out_button);
        logOutButton.setOnClickListener(logOutListener);

    }

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


private final View.OnLongClickListener usernameCopy =
        new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData usernameClip = ClipData.newPlainText("Username", ((TextView)findViewById(R.id.profile_userName)).getText());
                clipboard.setPrimaryClip(usernameClip);

                Toast.makeText(ProfileActivity.this, "Username Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

private final View.OnClickListener editPersonalInfoListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      Intent editPersonalInfoIntent = new Intent(ProfileActivity.this, EditPersonalInfoActivity.class);
                      startActivity(editPersonalInfoIntent);
            }
        };






}

