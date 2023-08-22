package com.example.wisewallet.activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.wisewallet.R;

import java.util.ArrayList;

public class CurrencyListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> currencies;
    private boolean[] selectedItems;
    private int selectedItemPosition = -1;

    public CurrencyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> currencies) {
        super(context, resource, currencies);
        this.context = context;
        this.currencies = currencies;
        selectedItems = new boolean[currencies.size()];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false);
        }

        TextView currencyTextView = convertView.findViewById(R.id.currency_text);
        currencyTextView.setText(currencies.get(position));

        if (selectedItems[position]) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.app_primary));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    public void selectItem(int position) {
        if (selectedItemPosition >= 0 && selectedItemPosition < selectedItems.length) {
            selectedItems[selectedItemPosition] = false;
        }
        selectedItems[position] = true;
        selectedItemPosition = position;
        notifyDataSetChanged();
    }

    public String getSelectedItem() {
        if (selectedItemPosition >= 0 && selectedItemPosition < currencies.size()) {
            return currencies.get(selectedItemPosition);
        }
        return null;
    }
}
