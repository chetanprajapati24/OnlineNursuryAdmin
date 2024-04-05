package com.chirag.admin.adminPlantPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Domain.Foods;
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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        // Retrieve data from Firebase Database
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Foods> orderList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Foods order = snapshot.getValue(Foods.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                // Set up the RecyclerView adapter
                if (mAdapter == null) {
                    mAdapter = new OrderAdapter(orderList, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    //mAdapter.updateData(orderList);
                    mAdapter.notifyDataSetChanged();
                }
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
}
