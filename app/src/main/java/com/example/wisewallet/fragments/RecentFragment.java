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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecentFragment extends Fragment {
    RecyclerView recyclerView;
    RecentRvAdapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_recent_, container,false);
        ArrayList<RecentItem> recentItemsArrayList = new ArrayList<>();

        DateFormat dateFormat = DateFormat.getDateInstance();
        String dateString = dateFormat.format(new Date());

        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));
        recentItemsArrayList.add(new RecentItem("Starbucks", "Food & Drinks", "$20", dateString));
        recentItemsArrayList.add(new RecentItem("Apple", "Technology", "$19.25", dateString));
        recentItemsArrayList.add(new RecentItem("Amazon", "Shopping", "$200", dateString));


        recyclerView = myView.findViewById(R.id.recent_activity_recycler_view);
        recyclerView.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(getContext());
        rvAdapter = new RecentRvAdapter(getContext(), recentItemsArrayList);
        recyclerView.setLayoutManager(rvLayoutManager);
        recyclerView.setAdapter(rvAdapter);

        return myView;
    }
}