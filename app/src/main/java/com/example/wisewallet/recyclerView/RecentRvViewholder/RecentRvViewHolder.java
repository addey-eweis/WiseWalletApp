package com.example.wisewallet.recyclerView.RecentRvViewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;

public class RecentRvViewHolder extends RecyclerView.ViewHolder{

    public final TextView merchant;
    public final TextView category;
    public final TextView dollarAmount;
    public final TextView date;

    public RecentRvViewHolder(@NonNull View itemView) {
        super(itemView);


        merchant = itemView.findViewById(R.id.analytics_merchant);
        category = itemView.findViewById(R.id.analytics_category);
        dollarAmount = itemView.findViewById(R.id.recent_dollar_amount);
        date = itemView.findViewById(R.id.recent_date);

    }

}
