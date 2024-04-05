package com.chirag.admin.adminPlantPanel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Domain.Foods;
import com.chirag.admin.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Foods> mOrdersList;
    private Context mContext;

    public OrderAdapter(List<Foods> ordersList, Context context) {
        this.mOrdersList = ordersList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods order = mOrdersList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return mOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, quantity, totalprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);
            quantity = itemView.findViewById(R.id.textViewQuantity);
            totalprice = itemView.findViewById(R.id.textViewTotalPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Foods clickedOrder = mOrdersList.get(position);
                        showOrderCompletionDialog(clickedOrder, position);
                    }
                }
            });
        }

        public void bind(Foods order) {
            title.setText(order.getTitle());
            description.setText(order.getDescription());
            quantity.setText(String.valueOf(order.getNumberInCart()));

            double total = order.getPrice() * order.getNumberInCart();
            totalprice.setText("Total: $" + String.format("%.2f", total));
        }

        private void showOrderCompletionDialog(final Foods order, final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Complete Order");
            builder.setMessage("Do you want to mark this order as completed?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    completeOrder(order.getKey(), position);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });

            // Adding a delete button
            builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteOrder(position);
                }
            });

            builder.show();
        }

        private void completeOrder(String key, final int position) {
            // Implement completion logic here
            // For example, update order status in the database

            // Assuming completion is successful
            Toast.makeText(mContext, "Order completed for " + mOrdersList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            // Remove item from the list
            mOrdersList.remove(position);
            notifyItemRemoved(position);
        }

        private void deleteOrder(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Delete Order");
            builder.setMessage("Are you sure you want to delete this order?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Remove item from the list
                    mOrdersList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mOrdersList.size());
                    Toast.makeText(mContext, "Order deleted", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });
            builder.show();
        }
    }
}
