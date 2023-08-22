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
import com.example.wisewallet.recyclerView.DAO.RecentItem;
import com.example.wisewallet.recyclerView.RecentRvAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class RecentFragment extends Fragment {
    RecyclerView recyclerView;
    RecentRvAdapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;

    FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID = firebaseAuth.getUid();


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

        View myView = inflater.inflate(R.layout.fragment_recent_, container,false);


        CollectionReference transactionsCollectionRef = firestoreDb.collection("users").document(userID).collection("transactions");

// Add the real-time listener to the transactions collection
        // Process the query snapshot and update the recentItemsArrayList
        // Update the UI with the new recentItemsArrayList
        ListenerRegistration listenerRegistration = transactionsCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (querySnapshot != null) {
                    ArrayList<RecentItem> recentItemsArrayList = new ArrayList<>();

                    // Process the query snapshot and update the recentItemsArrayList
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        if (document.getData().containsKey("transactionType") && document.getData().containsKey("transactionName")
                                && document.getData().containsKey("transactionCatagory") && document.getData().containsKey("transactionAmount")
                                && document.getData().containsKey("transactionCurrency") && document.getData().containsKey("transactionDate")) {

                            String transactionType = document.getString("transactionType");
                            String transactionName = document.getString("transactionName");
                            String transactionCatagory = document.getString("transactionCatagory");
                            String transactionAmount = document.getString("transactionAmount");
                            String transactionCurrency = document.getString("transactionCurrency");
                            String transactionDate = document.getString("transactionDate");

                            if (transactionType != null && transactionName != null && transactionCatagory != null
                                    && transactionAmount != null && transactionCurrency != null && transactionDate != null) {

                                RecentItem recentItem;
                                if (transactionType.equals("Income")) {
                                    recentItem = new RecentItem(transactionName, transactionCatagory, transactionAmount, "+", transactionCurrency, transactionDate);
                                } else {
                                    recentItem = new RecentItem(transactionName, transactionCatagory, transactionAmount, "-", transactionCurrency, transactionDate);
                                }


                                for (int i = 0; i <recentItemsArrayList.size(); i++) {
                                    String itemDate = recentItemsArrayList.get(i).getTransactionDate();

                                }
                                recentItemsArrayList.add(recentItem);
                            }
                        }
                    }

                    // Sort the ArrayList by date
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

        return myView;
    }



    }