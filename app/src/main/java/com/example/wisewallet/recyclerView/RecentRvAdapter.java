package com.example.wisewallet.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;
import com.example.wisewallet.recyclerView.DAO.RecentItem;
import com.example.wisewallet.recyclerView.RecentRvViewholder.RecentRvViewHolder;

import java.util.ArrayList;

public class RecentRvAdapter extends RecyclerView.Adapter<RecentRvViewHolder> {
    final Context context;
    final ArrayList<RecentItem> recentItems;

    public RecentRvAdapter(Context context, ArrayList<RecentItem> recentItems) {
        this.context = context;
        this.recentItems = recentItems;
    }

    @NonNull
    @Override
    public RecentRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recent_item, parent, false);
        return new RecentRvViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecentRvViewHolder holder, int position) {
        String transactionName = recentItems.get(position).getTransactionName();
        String transactionCatagory = recentItems.get(position).getTransactionCatagory();
        String transactionAmount = recentItems.get(position).getTransactionAmount();
        String transactionType = recentItems.get(position).getTransactionType();
        String transactionCurrency = recentItems.get(position).getTransactionCurrency();
        String transactionDate = recentItems.get(position).getTransactionDate();

        if(transactionType.equals("+")){
            holder.transactionType.setTextColor(Color.rgb(0,191,19));
            holder.transactionAmount.setTextColor(Color.rgb(0,191,19));
        }
        else{
            holder.transactionType.setTextColor(Color.RED);
            holder.transactionAmount.setTextColor(Color.RED);

        }

        holder.transactionName.setText(transactionName);
        holder.transactionCatagory.setText(transactionCatagory);
        holder.transactionAmount.setText(transactionAmount);
        holder.transactionType.setText(transactionType);
        holder.transactionCurrency.setText(transactionCurrency);
        holder.transactionDate.setText(transactionDate);

    }


    @Override
    public int getItemCount() {
        return recentItems.size();
    }


}
