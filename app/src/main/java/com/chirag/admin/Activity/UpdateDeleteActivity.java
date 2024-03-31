package com.chirag.admin.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chirag.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateDeleteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private EditText ratingEditText;
    private Button updateButton, deleteButton;
    private DatabaseReference databaseReference;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        titleEditText = findViewById(R.id.titleTxt);
        descriptionEditText = findViewById(R.id.descriptionTxt);
        priceEditText = findViewById(R.id.priceTxt);
        ratingEditText = findViewById(R.id.ratingTxt);
        updateButton = findViewById(R.id.btnUpdate);
        deleteButton = findViewById(R.id.btnDelete);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
            titleEditText.setText(extras.getString("title"));
            descriptionEditText.setText(extras.getString("description"));
            priceEditText.setText(String.valueOf(extras.getDouble("price")));
            ratingEditText.setText(String.valueOf(extras.getDouble("star")));
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFood();
            }
        });

    }

    private void updateFood() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        double star = Double.parseDouble(ratingEditText.getText().toString());

        if (key != null) {
            DatabaseReference foodRef = databaseReference.child("Foods").child(key);

            // Create a map to hold the updated fields
            Map<String, Object> updates = new HashMap<>();
            updates.put("title", title);
            updates.put("description", description);
            updates.put("price", price);
            updates.put("star", star);

            // Update the specific fields without overwriting others
            foodRef.updateChildren(updates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(UpdateDeleteActivity.this, "Food updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateDeleteActivity.this, "Failed to update food: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(UpdateDeleteActivity.this, "Null key received", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFood() {
        if (key != null) {
            DatabaseReference foodRef = databaseReference.child("Foods").child(key);

            // Remove the item from the database
            foodRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UpdateDeleteActivity.this, "Plany deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after successful deletion
                    } else {
                        Toast.makeText(UpdateDeleteActivity.this, "Failed to delete Plant: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(UpdateDeleteActivity.this, "Null key received", Toast.LENGTH_SHORT).show();
        }
    }
}
