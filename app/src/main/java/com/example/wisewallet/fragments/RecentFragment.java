package com.example.wisewallet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;
import com.example.wisewallet.firebaseHandeling.FirebaseOperationsManager;
import com.example.wisewallet.recyclerView.DAO.RecentItem;
import com.example.wisewallet.recyclerView.RecentRvAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class RecentFragment extends Fragment {
    RecyclerView recyclerView;
    RecentRvAdapter rvAdapter;
    RecentItem recentItem;
    RecyclerView.LayoutManager rvLayoutManager;
    FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
    String Currency;

    String userID = FirebaseOperationsManager.getInstance().getUserId(getContext());
    CollectionReference transactionsCollectionRef = firestoreDb.collection("users").document(userID).collection("transactions");



    private void sortByDate(ArrayList<RecentItem> recentItemsArrayList) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        Collections.sort(recentItemsArrayList, new Comparator<RecentItem>() {
            @Override
            public int compare(RecentItem item1, RecentItem item2) {
                try {
                    Date date1 = dateFormat.parse(item1.getTransactionDate());
                    Date date2 = dateFormat.parse(item2.getTransactionDate());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FirebaseOperationsManager.getInstance().getCurrency(getContext(), new FirebaseOperationsManager.FirebaseCurrencyCallback() {
            @Override
            public void onCurrencyRead(String currency) {
                Currency = currency;
            }
        });
        ArrayList<RecentItem> recentItemsArrayList = new ArrayList<>();

        View myView = inflater.inflate(R.layout.fragment_recent_, container, false);
        recyclerView = myView.findViewById(R.id.recent_activity_recycler_view); // Moved recyclerView initialization here

// Fetch documents from the collection
        transactionsCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    return;
                }

                if (querySnapshot != null) {

                    // Process the query snapshot and update the recentItemsArrayList
                    for (DocumentSnapshot document : querySnapshot) {
                        if (document.exists()) {
                        Map<String, Object> transactionData = document.getData();
                        String transactionType = document.getString("transactionType");
                        String transactionName = document.getString("transactionName");
                        String transactionCatagory = document.getString("transactionCatagory");
                        String transactionAmount = document.getString("transactionAmount");
                        String transactionCurrency = Currency;
                        String transactionDate = document.getString("transactionDate");

//                        Toast.makeText(getContext(), transactionName + transactionType + transactionCatagory + transactionAmount + transactionCurrency + transactionDate, Toast.LENGTH_LONG).show();

                        if (transactionType.equals("Income")) {
                                recentItem = new RecentItem(transactionName, transactionCatagory, transactionAmount, "+", transactionCurrency, transactionDate);
                            } else {
                                recentItem = new RecentItem(transactionName, transactionCatagory, transactionAmount, "-", transactionCurrency, transactionDate);
                            }
//                        Toast.makeText(getContext(), recentItemsArrayList.toString(), Toast.LENGTH_SHORT).show();
                    }
                        recentItemsArrayList.add(recentItem);
                }

                // Now, recentItemsArrayList contains the RecentItem objects with data from the documents
                // You can use this ArrayList for further processing
                sortByDate(recentItemsArrayList);
                // Update the UI with the new recentItemsArrayList
                rvAdapter = new RecentRvAdapter(getContext(), recentItemsArrayList);
                recyclerView = myView.findViewById(R.id.recent_activity_recycler_view);
                recyclerView.setHasFixedSize(false);
                rvLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(rvLayoutManager);
                recyclerView.setAdapter(rvAdapter);
                }
            }
        });

        // Sort the ArrayList by date

        return myView;
    }

    }