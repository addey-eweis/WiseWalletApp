package com.example.wisewallet.recyclerView.RecentRvViewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;

public class BudgetGoalViewholder extends RecyclerView.ViewHolder{
    public TextView goalName, budgetAmount, savedUp, goalAmount, budgetPeriod, currencyOne, currencyTwo, currencyThree, expectedDate;
    public BudgetGoalViewholder(@NonNull View itemView) {
        super(itemView);

        goalName = itemView.findViewById(R.id.item_name);
        budgetAmount = itemView.findViewById(R.id.budget_goal_fragment_amount);
        currencyOne = itemView.findViewById(R.id.budget_goal_fragment_currency_1);
        savedUp = itemView.findViewById(R.id.amount_saved_up);
        currencyTwo = itemView.findViewById(R.id.budget_goal_fragment_currency_2);
        goalAmount = itemView.findViewById(R.id.goal_amount);
        currencyThree = itemView.findViewById(R.id.budget_goal_fragment_currency_3);
        budgetPeriod = itemView.findViewById(R.id.budget_period);
        expectedDate = itemView.findViewById(R.id.expected_date_actual);

    }
}