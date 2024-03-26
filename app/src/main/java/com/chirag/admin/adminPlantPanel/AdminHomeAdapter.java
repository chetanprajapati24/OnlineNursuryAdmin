package com.chirag.admin.adminPlantPanel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Activity.UpdateDeleteActivity;
import com.chirag.admin.Domain.Foods;
import com.chirag.admin.R;

import java.util.List;

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.ViewHolder> {
    private List<Foods> food;

    public AdminHomeAdapter(List<Foods> foodsList) {
        this.food = foodsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Foods foods = this.food.get(position);
        holder.titleTextView.setText(foods.getTitle());
        holder.descriptionTextView.setText(foods.getDescription());
        holder.priceTextView.setText(String.valueOf(foods.getPrice()));

        // Set other data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateDeleteActivity.class);
                intent.putExtra("key", foods.getKey());
                intent.putExtra("title", foods.getTitle());
                intent.putExtra("description", foods.getDescription());
                intent.putExtra("price", foods.getPrice());
                intent.putExtra("star", foods.getStar());
                v.getContext().startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        TextView ratingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView != null) {
                titleTextView = itemView.findViewById(R.id.titleTxt);
                descriptionTextView = itemView.findViewById(R.id.descriptionTxt);
                priceTextView = itemView.findViewById(R.id.priceTxt);
                ratingTextView=itemView.findViewById(R.id.ratingTxt);
            }
        }
    }
}
