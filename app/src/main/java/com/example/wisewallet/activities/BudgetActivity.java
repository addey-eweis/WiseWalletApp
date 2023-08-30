package com.example.wisewallet.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BudgetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String period;
    String currency;
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
    //Get UserId
    String userId = firebaseOperationsManager.getUserId(BudgetActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Check Authentication Status
        FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
        firebaseOperationsManager.checkAuthentication(BudgetActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Spinner budgetPeriodSpinner = findViewById(R.id.budget_period_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.period, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budgetPeriodSpinner.setAdapter(arrayAdapter);
        budgetPeriodSpinner.setOnItemSelectedListener(this);

        //Get Document Ref
        DocumentReference currencyReference = FirebaseFirestore.getInstance().collection("users").document(userId);

        firebaseOperationsManager.readFromFirebase(BudgetActivity.this, currencyReference, new FirebaseOperationsManager.FirebaseReadCallback() {
            @Override
            public void onDataRead(Map<String, Object> dataMap) {
                currency = Objects.requireNonNull(dataMap.get("currency")).toString();
                TextView currencyTextView = findViewById(R.id.currency_view);
                TextView currencyTextView2 = findViewById(R.id.currency_view2);
                currencyTextView.setText(currency);
                currencyTextView2.setText(currency);
            }
        });
                MaterialButton budgetButton = findViewById(R.id.save_button_budget);
                budgetButton.setOnClickListener(budgetButtonListener);

    }

    private final View.OnClickListener budgetButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText limitAmount = findViewById(R.id.budget_limit_amount_field);
                    EditText goalName = findViewById(R.id.goal_name_field);
                    EditText goalAmount = findViewById(R.id.goal_cost_field);
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    DocumentReference budgetAndGoalsPath = firestore.collection("users").document(auth.getCurrentUser().getUid()).collection("budget&goals").document();
                    Map<String, Object> budgetAndGoals = new HashMap<>();

//                    period;
                    if((TextUtils.isEmpty(limitAmount.getText()) | limitAmount.getText().equals("0")) | period.equals("Select Budget Period")){
                        Toast.makeText(BudgetActivity.this, "Please Enter budget fields", Toast.LENGTH_SHORT).show();

                    } else if ((!TextUtils.isEmpty(limitAmount.getText()) | !period.equals("Select Budget Period")) & (TextUtils.isEmpty(goalAmount.getText()) & TextUtils.isEmpty(goalName.getText()))) {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                        Toast.makeText(BudgetActivity.this, "Goal Not Set", Toast.LENGTH_SHORT).show();
                        budgetAndGoals.put("budgetPeriod", period);
                        budgetAndGoals.put("budgetLimit", limitAmount.getText().toString());
                        budgetAndGoals.put("type", "budgetOnly");
                        budgetAndGoals.put("date", formatter.format(date));
                        budgetAndGoalsPath.set(budgetAndGoals).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(BudgetActivity.this, "Budget Set Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BudgetActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if ((!TextUtils.isEmpty(limitAmount.getText()) | !period.equals("Select Budget Period")) & (!TextUtils.isEmpty(goalAmount.getText()) & !TextUtils.isEmpty(goalName.getText()))){
                        budgetAndGoals.put("budgetPeriod", period);
                        budgetAndGoals.put("budgetLimit", limitAmount.getText().toString());
                        budgetAndGoals.put("goalName", goalName.getText().toString());
                        budgetAndGoals.put("goalAmount", goalAmount.getText().toString());
                        budgetAndGoals.put("type", "budget&goal");
                        budgetAndGoals.put("date",  LocalDateTime.now());
                        budgetAndGoalsPath.set(budgetAndGoals).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(BudgetActivity.this, "Budget & Goal Set Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BudgetActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(BudgetActivity.this, "Please Enter fields", Toast.LENGTH_SHORT).show();
                    }
                }
            };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        period = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}