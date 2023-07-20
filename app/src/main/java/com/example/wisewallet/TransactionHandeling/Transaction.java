package com.example.wisewallet.TransactionHandeling;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private String userId;
    private String transactionType;
    private String transactionName;
    private String transactionAmount;
    private String transactionDate;
    private String transactionCategory;
    private FirebaseFirestore firestoreDb;
    private Context context;
    private String total;

    public Transaction() {
    }

    public Transaction(String userId, String transactionType, String transactionName, String transactionAmount, String transactionDate, String transactionCategory, Context context) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionCategory = transactionCategory;
        this.firestoreDb = FirebaseFirestore.getInstance(); // Initialize the firestoreDb field
        this.context = context;
    }


    @NonNull
    private Map<String, Object> firebaseTransactionHashmap(){
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("transactionType", transactionType);
        transactionData.put("transactionName", transactionName);
        transactionData.put("transactionAmount", transactionAmount);
        transactionData.put("transactionDate", transactionDate);
        transactionData.put("transactionCatagory", transactionCategory);

        return transactionData;
    }

    public String firebaseTransactionSubmission(){
        CollectionReference transactionsRef = firestoreDb.collection("users").document(userId).collection("transactions");
        DocumentReference newTransactionRef = transactionsRef.document();
        String transactionId = newTransactionRef.getId();
        DocumentReference totalDocument = firestoreDb.collection("users").document(userId);

        firestoreDb.collection("users").document(userId).collection("transactions").document(transactionId)
                .set(firebaseTransactionHashmap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if(firebaseTransactionHashmap().containsValue("Income")){
                            totalDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    total = documentSnapshot.getString("total");
                                    Map<String, Object> totalHashmap = new HashMap<>();

                                    if (total != null && transactionAmount != null) {
                                        totalHashmap.put("total", String.valueOf(Integer.parseInt(total) + Integer.parseInt(transactionAmount)));
                                    }


                                    totalDocument.update(totalHashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context.getApplicationContext(), "Successfully added to total", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        } else if (firebaseTransactionHashmap().containsValue("Expense")) {
                            totalDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    total = documentSnapshot.getString("total");
                                    Map<String, Object> totalHashmap = new HashMap<>();

                                    if (total != null) {
                                        totalHashmap.put("total", String.valueOf(Integer.parseInt(total) - Integer.parseInt(transactionAmount)));                                    }

                                    totalDocument.update(totalHashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context.getApplicationContext(), "Successfully subtracted from total", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Processing Failed, Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });



        return transactionId;

    }



}