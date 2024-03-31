package com.chirag.admin.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chirag.admin.Domain.Foods;
import com.chirag.admin.R;
import com.chirag.admin.adminPlantPanel.AdminOrderFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_activity);

        // Create yourOrdersList, a list of Foods objects
        List<Foods> yourOrdersList = createYourOrdersList(); // Initialize your list here

        // Create a new instance of AdminOrderfragment and set arguments
        AdminOrderFragment fragment = new AdminOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable("ordersList", (Serializable) yourOrdersList);
        fragment.setArguments(args);

        // Now replace or add the fragment using FragmentManager
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // Changed to R.id.fragment_container
                .commit();
    }

    // This method creates and returns a List<Foods> object, replace it with your actual implementation
    private List<Foods> createYourOrdersList() {
        // Implement the logic to create your list of Foods objects here
        List<Foods> ordersList = new ArrayList<>();
        // Add Foods objects to the list
        // For example:
        ordersList.add(new Foods(/* Parameters for Foods constructor */));
        // Add more Foods objects as needed
        return ordersList;
    }
}
