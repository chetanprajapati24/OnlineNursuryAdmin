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

import com.chirag.admin.Domain.Order;
import com.chirag.admin.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> mOrdersList;
    private Context mContext;

    public OrderAdapter(List<Order> ordersList, Context context) {
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
        Order order = mOrdersList.get(position);
        // Bind order data to views in the ViewHolder
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
                        Order clickedOrder = mOrdersList.get(position);
                        showOrderCompletionDialog(clickedOrder, position);
                    }
                }
            });
        }

        public void bind(Order order) {
            // Bind order data to views
            title.setText(order.getTitle()); // Example, you can bind other properties of Foods as needed
            description.setText(order.getDescription());
            quantity.setText(String.valueOf(order.getNumberInCart()));

            // Calculate and set the total price
            double total = order.getPrice() * order.getNumberInCart(); // Assuming getPrice() returns the price of one item
            totalprice.setText("Total: $" + String.format("%.2f", total));
        }

        private void showOrderCompletionDialog(final Order order, final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Complete Order");
            builder.setMessage("Do you want to mark this order as completed?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform action when the order is completed
                    // You can also notify the user that the order is completed
                    completeOrder(order.getKey(), position);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing when the order is not completed
                }
            });
            builder.show();
        }
    }

    private void completeOrder(String key, final int position) {
        // Implement completion logic here
        // For example, you can update the order status in the database

        // Assuming completion is successful
        Toast.makeText(mContext, "Order completed for " + mOrdersList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

        // Remove item from the list
        mOrdersList.remove(position);
        notifyItemRemoved(position);
    }
}

