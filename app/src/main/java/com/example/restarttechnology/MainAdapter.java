package com.example.restarttechnology;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataSet;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cardIcon;
        TextView cardText;

        MyViewHolder(View itemView) {
            super(itemView);
            this.cardIcon = itemView.findViewById(R.id.cardViewIcon);
            this.cardText = itemView.findViewById(R.id.cardViewText);
        }
    }

    MainAdapter(ArrayList<DataModel> myDataSet) {
        this.dataSet = myDataSet;
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageView cardIcon = holder.cardIcon;
        TextView cardText = holder.cardText;

        cardIcon.setImageResource(dataSet.get(position).getImage());
        cardText.setText(dataSet.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}