package com.example.wisewallet.recyclerView.RecentRvViewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;

public class BudgetGoalViewholder extends RecyclerView.ViewHolder {
    TextView itemName;
    TextView budgetAmount;
    TextView itemSavedPrice;
    TextView itemPrice;
    TextView budgetPeriod;
    TextView currencyOne;
    TextView currencyTwo;
    TextView currencyThree;
    TextView expectedDate;
    View view;

    public BudgetGoalViewholder(@NonNull View itemView) {
        super(itemView);

        itemName = (TextView) itemView.findViewById(R.id.item_name);
        budgetAmount = (TextView) itemView.findViewById(R.id.budget_goal_fragment_amount);
        itemSavedPrice = (TextView) itemView.findViewById(R.id.amount_saved_up);
        itemPrice = (TextView) itemView.findViewById(R.id.goal_amount);
        budgetPeriod = (TextView) itemView.findViewById(R.id.budget_period);
        currencyOne = (TextView) itemView.findViewById(R.id.budget_goal_fragment_currency_1);
        currencyTwo = (TextView) itemView.findViewById(R.id.budget_goal_fragment_currency_2);
        currencyThree = (TextView) itemView.findViewById(R.id.budget_goal_fragment_currency_3);
        expectedDate = (TextView) itemView.findViewById(R.id.expected_date_actual);

    }
}
