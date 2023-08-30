package com.example.wisewallet.recyclerView;//package com.example.wisewallet.recyclerView;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.wisewallet.R;
//import com.example.wisewallet.recyclerView.DAO.BudgetGoalItem;
//
//import java.util.List;
//
//public class BudgetRvAdapter extends RecyclerView.Adapter<BudgetRvAdapter.ViewHolder> {
//
//    private final Context context;
//    private final List<BudgetGoalItem> budgetList;
//
//    public BudgetRvAdapter(Context context, List<BudgetGoalItem> budgetList) {
//        this.context = context;
//        this.budgetList = budgetList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.budget_goal_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        BudgetGoalItem item = budgetList.get(position);
//
//        // Bind data to your views in the ViewHolder
//        holder.itemName.setText(item.getItemName());
//        holder.budgetAmount.setText(item.getBudgetAmount());
//        holder.itemSavedPrice.setText(item.getSavedUpAmount());
//        holder.itemPrice.setText(item.getItemPrice());
//        holder.budgetPeriod.setText(item.getBudgetPeriod());
//        holder.currencyOne.setText(item.getCurrency());
//        holder.currencyTwo.setText(item.getCurrency());
//        holder.currencyThree.setText(item.getCurrency());
//        holder.expectedDate.setText(item.getExpectedDate());
//        // You can bind more data here if needed
//    }
//
//    @Override
//    public int getItemCount() {
//        return budgetList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView itemName;
//        TextView budgetAmount;
//        TextView itemSavedPrice;
//        TextView itemPrice;
//        TextView budgetPeriod;
//        TextView currencyOne;
//        TextView currencyTwo;
//        TextView currencyThree;
//        TextView expectedDate;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemName = itemView.findViewById(R.id.item_name);
//            budgetAmount = itemView.findViewById(R.id.budget_goal_fragment_amount);
//            itemSavedPrice = itemView.findViewById(R.id.amount_saved_up);
//            itemPrice = itemView.findViewById(R.id.goal_amount);
//            budgetPeriod = itemView.findViewById(R.id.budget_period);
//            currencyOne = itemView.findViewById(R.id.budget_goal_fragment_currency_1);
//            currencyTwo = itemView.findViewById(R.id.budget_goal_fragment_currency_2);
//            currencyThree = itemView.findViewById(R.id.budget_goal_fragment_currency_3);
//            expectedDate = itemView.findViewById(R.id.expected_date_actual);
//            // Initialize other views here if needed
//        }
//    }
//}


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisewallet.R;
import com.example.wisewallet.recyclerView.DAO.BudgetGoalItem;

import java.util.List;

public class BudgetRvAdapter extends RecyclerView.Adapter<BudgetRvAdapter.MyViewHolder> {
    Context context;
    List<BudgetGoalItem> budgetGoalItemsList;


    public BudgetRvAdapter(Context context, List<BudgetGoalItem> budgetGoalItemsList) {
        this.context = context;
        this.budgetGoalItemsList = budgetGoalItemsList;
    }

    @NonNull
    @Override
    public BudgetRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.budget_goal_item, parent, false);

        return new BudgetRvAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetRvAdapter.MyViewHolder holder, int position) {
        holder.goalName.setText(budgetGoalItemsList.get(position).getGoalName());
        holder.budgetAmount.setText(budgetGoalItemsList.get(position).getBudgetAmount());
        holder.currencyOne.setText(budgetGoalItemsList.get(position).getCurrency());
        holder.savedUp.setText(budgetGoalItemsList.get(position).getSavedUpAmount());
        holder.currencyTwo.setText(budgetGoalItemsList.get(position).getCurrency());
        holder.goalAmount.setText(budgetGoalItemsList.get(position).getGoalAmount());
        holder.currencyThree.setText(budgetGoalItemsList.get(position).getCurrency());
        holder.budgetPeriod.setText(budgetGoalItemsList.get(position).getBudgetPeriod());
        holder.expectedDate.setText(budgetGoalItemsList.get(position).getExpectedDate());


    }

    @Override
    public int getItemCount() {
        return budgetGoalItemsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView goalName, budgetAmount, savedUp, goalAmount, budgetPeriod, currencyOne, currencyTwo, currencyThree, expectedDate;
        public MyViewHolder(@NonNull View itemView) {
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
}