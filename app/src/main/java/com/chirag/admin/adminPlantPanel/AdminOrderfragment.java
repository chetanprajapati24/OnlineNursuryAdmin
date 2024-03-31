package com.chirag.admin.adminPlantPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Domain.Order;
import com.chirag.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private DatabaseReference mDatabaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_orders, container, false);
        getActivity().setTitle("New Orders");

        setHasOptionsMenu(true); // Enable options menu
        mRecyclerView = v.findViewById(R.id.Recycle_menu);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize Firebase Database reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("orders");

        // Retrieve data from Firebase Database
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Order> foodsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order food = snapshot.getValue(Order.class);
                    if (food != null) {
                        foodsList.add(food);
                    }
                }
                // Set up the RecyclerView adapter
                mAdapter = new OrderAdapter(foodsList, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("FirebaseDatabase", "Error fetching data", databaseError.toException());
                Toast.makeText(getActivity(), "Error fetching data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.report, menu);
    }
}
