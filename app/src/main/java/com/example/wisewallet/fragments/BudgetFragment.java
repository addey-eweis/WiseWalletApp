package com.example.wisewallet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.example.wisewallet.recyclerView.BudgetRvAdapter;
import com.example.wisewallet.recyclerView.DAO.BudgetGoalItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class BudgetFragment extends Fragment {
    FirebaseOperationsManager firebaseOperationsManager = FirebaseOperationsManager.getInstance();
    CollectionReference budgetTransactionCollection;
    BudgetGoalItem budgetGoalItem;
    String Currency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics_, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.budgetsAndGoals);
        budgetTransactionCollection = FirebaseFirestore.getInstance().collection("users").document(firebaseOperationsManager.getUserId(getContext())).collection("budget&goals");

        FirebaseFirestore.getInstance().collection("users").document(firebaseOperationsManager.getUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Currency = task.getResult().getString("currency");
            }
        });


        budgetTransactionCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    return;
                }

                if (querySnapshot != null) {
                    List<BudgetGoalItem> budgetGoalItems = new ArrayList<>();
                    // Process the query snapshot and update the recentItemsArrayList
                    for (DocumentSnapshot document : querySnapshot) {
                        if (document.exists()) {
                            String goalName = document.getString("goalName");
                            String goalPrice = document.getString("goalAmount");
                            String budgetAmount = document.getString("budgetLimit");
                            String budgetPeriod = document.getString("budgetPeriod");
                            String savedUpAmount = document.getString("savedUpAmount");
//                            String savedUpAmount = "0";
//                            String date = document.getString("date");


//                            String type = document.getString("type");
                            //Insert Type later for either view-holder, one with goal or one without
                            budgetGoalItem = new BudgetGoalItem(goalName,budgetAmount, Currency, savedUpAmount, goalPrice, budgetPeriod, expectedDate(goalPrice, budgetAmount, budgetPeriod, document.getId()));

                            budgetGoalItems.add(budgetGoalItem);

                            BudgetRvAdapter adapter = new BudgetRvAdapter(getContext(), budgetGoalItems);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }
                }
            }
        });


        return view;
    }


    public void calculateSavedUpAmount(String passedBudgetLimit, float deductionFrequency, String budgetDocumentId){
        System.out.println("SavedUpAmount");
        DocumentReference firebaseObj = FirebaseFirestore.getInstance().collection("users").document(firebaseOperationsManager.getUserId(getContext()));
        Log.d("thisDoc", "onDataRead: " + firebaseObj);
        firebaseOperationsManager.readFromFirebase(getContext(), firebaseObj, new FirebaseOperationsManager.FirebaseReadCallback() {
            @Override
            public void onDataRead(Map<String, Object> dataMap) {
                int totalIncome = Integer.parseInt(String.valueOf(dataMap.get("totalIncome")));
                int budgetLimit = Integer.parseInt(passedBudgetLimit);
                Log.d("dataMap", String.valueOf(dataMap));

                //Subtract from totalIncome Daily and add to savedUpAmount daily
                int savedUpAmount = totalIncome - budgetLimit;
                Log.d("savedUpAmount", String.valueOf(savedUpAmount));

                ///Total Amount To Deduct For The Whole Period
                double totalAmountToDeductForTheWholePeriod = savedUpAmount * deductionFrequency;


               

                /*****************************************************************************************/

                Map<String, Object> totalIncomeMap = new HashMap<>();
                totalIncomeMap.put("totalIncome", String.valueOf(savedUpAmount));

                // Deduct from totalIncome the budget limit every "something" days/weeks/months/years...
                firebaseOperationsManager.submitToFirebase(getContext(), firebaseObj, totalIncomeMap, new FirebaseOperationsManager.FirebaseSubmitCallback() {
                    @Override
                    public void onSubmitSuccess() {

                    }

                    @Override
                    public void onSubmitFailure(String errorMessage) {
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

                    }
                });

//                submit to firebase
                Map<String, Object> savedUpAmountMap = new HashMap<>();
//                savedUpAmountMap.put("savedUpAmount", put saved up amount here)
                DocumentReference budgetfirebaseObj = FirebaseFirestore.getInstance().collection("users").document(firebaseOperationsManager.getUserId(getContext())).collection("budget&goals").document(budgetDocumentId);
                firebaseOperationsManager.submitToFirebase(getContext(), budgetfirebaseObj, savedUpAmountMap, new FirebaseOperationsManager.FirebaseSubmitCallback() {
                    @Override
                    public void onSubmitSuccess() {

                    }

                    @Override
                    public void onSubmitFailure(String errorMessage) {

                    }
                });
            }
        });

    }


    private String expectedDate(String goalPrice, String budgetAmount, String budgetPeriod, String budgetDocumentId){
        float days = 0;

        float parsedBudgetAmount = Float.parseFloat(budgetAmount);
        if (parsedBudgetAmount != 0) {
            switch (budgetPeriod) {
                case "Daily":
                    days = Float.parseFloat(goalPrice) / parsedBudgetAmount;
                    //Subtract from inflows Daily
                    calculateSavedUpAmount(budgetAmount,  days, budgetDocumentId);

                    break;
                case "Weekly":
                    days = Float.parseFloat(goalPrice) / (parsedBudgetAmount / 7);
                    //Subtract from inflows Weekly
                    calculateSavedUpAmount(budgetAmount,  days, budgetDocumentId);
                    break;
                case "Monthly":
                    days = Float.parseFloat(goalPrice) / (parsedBudgetAmount / 30);
                    //Subtract from inflows Monthly
                    calculateSavedUpAmount(budgetAmount,  days, budgetDocumentId);
                    break;
                case "Yearly":
                    days = Float.parseFloat(goalPrice) / (parsedBudgetAmount / 365);
                    //Subtract from inflows Yearly
                    calculateSavedUpAmount(budgetAmount, days, budgetDocumentId);

                    break;
            }
        }

        LocalDate startDate = java.time.LocalDate.now();
        LocalDate endDate = startDate.plusDays(Math.round(days));

        Timer myDeductionTimer = new Timer();
        TimerTask taskToRun = new TimerTask() {
            @Override
            public void run() {

            }
        };


        myDeductionTimer.schedule(taskToRun, startDate, endDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(endDate);
    }

}
