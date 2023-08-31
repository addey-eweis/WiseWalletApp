package com.example.wisewallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssetsActivity extends AppCompatActivity {


    FirebaseFirestore database;
    Map<String, Object> assets = new HashMap<>();
    String currency;
    FirebaseAuth firebaseAuth;
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
    String userId = firebaseOperationsManager.getUserId(AssetsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);


        TextView currencyAssetsField = findViewById(R.id.currency_view);
        MaterialButton saveButton = findViewById(R.id.save_button_assets);

        DocumentReference currencyReference = FirebaseFirestore.getInstance().collection("users").document(userId);

        firebaseOperationsManager.readFromFirebase(AssetsActivity.this, currencyReference, new FirebaseOperationsManager.FirebaseReadCallback() {
            @Override
            public void onDataRead(Map<String, Object> dataMap) {
                currency = Objects.requireNonNull(dataMap.get("currency")).toString();
                currencyAssetsField.setText(currency);
            }
        });


        saveButton.setOnClickListener(saveButtonListener);

    }

    private final View.OnClickListener saveButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText currentAssetsField = findViewById(R.id.current_assets_field);
                    if(currentAssetsField.getText() != null){
                    assets.put("totalIncome", currentAssetsField.getText().toString());
                    assets.put("totalExpenses", "0");
                    }

                    if (!currentAssetsField.getText().toString().equals("")) {
                        DocumentReference currentAssetsReference = FirebaseFirestore.getInstance().collection("users").document(userId);
                        firebaseOperationsManager.submitToFirebase(AssetsActivity.this, currentAssetsReference, assets, new FirebaseOperationsManager.FirebaseSubmitCallback() {
                            @Override
                            public void onSubmitSuccess() {
                                Toast.makeText(getApplicationContext(), "Welcome to ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AssetsActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onSubmitFailure(String errorMessage) {
                            }
                        });

                    }
                    else{
                        Toast.makeText(AssetsActivity.this, "Please Enter In the Required Field", Toast.LENGTH_SHORT).show();
                    }

                }
            };
}