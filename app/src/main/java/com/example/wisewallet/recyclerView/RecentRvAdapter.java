package com.example.wisewallet.recyclerView;

import android.content.Context;
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
        String dollarAmountText = recentItems.get(position).getDollarText();
        String categoryText = recentItems.get(position).getCategoryText();
        String merchantText = recentItems.get(position).getMerchantText();
        String dateText = recentItems.get(position).getDateText();

        holder.dollarAmount.setText(dollarAmountText);
        holder.category.setText(categoryText);
        holder.merchant.setText(merchantText);
        holder.date.setText(dateText);

    }

    @Override
    public int getItemCount() {
        return recentItems.size();
    }


}
