package com.chirag.admin.adminPlantPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Domain.Foods;
import com.chirag.admin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdminHomeFragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private RecyclerView recyclerView;
    private List<Foods> foods;
    private AdminHomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chef_home, container, false);

        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Initialize views
        recyclerView = view.findViewById(R.id.Recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        foods = new ArrayList<>();
        adapter = new AdminHomeAdapter(foods);
        recyclerView.setAdapter(adapter);


        // Set up ValueEventListener to fetch data
        ValueEventListener foodListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear previous data
                foods.clear();

                // Iterate through each child node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Foods food = snapshot.getValue(Foods.class);
                    foods.add(food);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        };

        // Attach ValueEventListener
        databaseReference.addValueEventListener(foodListener);

        return view;
    }
}