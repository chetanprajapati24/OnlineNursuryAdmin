package com.chirag.admin.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chirag.admin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateDeleteActivity extends AppCompatActivity {
    EditText editTitle, editDescription, editPrice, editRating;
    Button btnUpdate;

    DatabaseReference databaseReference;

    String foodId; // Variable to store the key of the item to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        editTitle = findViewById(R.id.titleTxt);
        editDescription = findViewById(R.id.descriptionTxt);
        editPrice = findViewById(R.id.priceTxt);
        editRating = findViewById(R.id.ratingTxt);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Initialize Firebase components
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        // Retrieve data from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            foodId = extras.getString("id"); // Use the correct key to retrieve food ID
            String title = extras.getString("title");
            String description = extras.getString("description");
            double price = extras.getDouble("price");
            double star = extras.getDouble("star");

            // Set retrieved data to EditText fields
            editTitle.setText(title);
            editDescription.setText(description);
            editPrice.setText(String.valueOf(price));
            editRating.setText(String.valueOf(star));
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated data from EditText fields
                String updatedTitle = editTitle.getText().toString();
                String updatedDescription = editDescription.getText().toString();
                double updatedPrice = Double.parseDouble(editPrice.getText().toString());
                double updatedRating = Double.parseDouble(editRating.getText().toString());

                // Update the data in Firebase
                updateData(updatedTitle, updatedDescription, updatedPrice, updatedRating);
            }
        });
    }

    private void updateData(String title, String description, double price, double star) {
        // Create a map with updated data
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("title", title);
        updateData.put("description", description);
        updateData.put("price", price);
        updateData.put("star", star);

        // Update the data in Firebase
        databaseReference.child(foodId).updateChildren(updateData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Data updated successfully
                        Toast.makeText(UpdateDeleteActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Failed to update data
                        Toast.makeText(UpdateDeleteActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
