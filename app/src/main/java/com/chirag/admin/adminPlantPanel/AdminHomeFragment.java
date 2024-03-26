package com.chirag.admin.adminPlantPanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chirag.admin.Domain.Foods;
import com.chirag.admin.MainMenu;
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

        setHasOptionsMenu(true); // Enable options menu

        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Foods");
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
                    if(food != null){
                        food.setKey(snapshot.getKey());
                    }
                    foods.add(food);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("AdminHomeFragment", "Database error: " + databaseError.getMessage());
                // Notify the user about the error, e.g., through a toast or Snackbar
                Toast.makeText(getActivity(), "Failed to load data. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        };

        // Attach ValueEventListener
        databaseReference.addValueEventListener(foodListener);

        // Return the inflated view
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idd = item.getItemId();
        if(idd == R.id.LOGOUT){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), MainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
