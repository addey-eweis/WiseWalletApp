package com.example.wisewallet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;

public class EditPersonalInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);



        // Save
        Button saveButton = findViewById(R.id.editInfoSubmit);
        saveButton.setOnClickListener(saveListener);
    }



    private final View.OnClickListener saveListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String getName = ((EditText)findViewById(R.id.profile_name_changeable_editable)).getText().toString();
                    String getUsername = ((EditText)findViewById(R.id.profile_username_changeable_editable)).getText().toString();
                    String getEmail = ((EditText)findViewById(R.id.profile_email_changeable_editable)).getText().toString();
                    String getPassword = ((EditText)findViewById(R.id.profile_password_changeable_editable)).getText().toString();
                }
            };
}
