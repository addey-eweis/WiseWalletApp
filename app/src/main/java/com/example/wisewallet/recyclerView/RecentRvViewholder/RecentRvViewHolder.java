package com.example.wisewallet.recyclerView.RecentRvViewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;

public class RecentRvViewHolder extends RecyclerView.ViewHolder{

    public final TextView transactionName;
    public final TextView transactionCatagory;
    public final TextView transactionAmount;
    public final TextView transactionType;
    public final TextView transactionCurrency;
    public final TextView transactionDate;

    public RecentRvViewHolder(@NonNull View itemView) {
        super(itemView);

        transactionName = itemView.findViewById(R.id.analytics_merchant);
        transactionCatagory = itemView.findViewById(R.id.analytics_category);
        transactionAmount = itemView.findViewById(R.id.recent_dollar_amount);
        transactionType = itemView.findViewById(R.id.recent_type);
        transactionCurrency = itemView.findViewById(R.id.recent_currency);
        transactionDate = itemView.findViewById(R.id.recent_date);

    }

}
