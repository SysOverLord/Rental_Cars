package com.example.RentalCars;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Entity.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Queue;

public class SearchActivity extends AppCompatActivity {

    private Queue searchQueue;

    private  void createSearchQueue(String fBrand,String fModel, int page) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + fBrand + "/" + fModel);
        Query query = myRef.orderByChild("model").equalTo(fModel);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (searchQueue.size() < 5) {
                        searchQueue.add(dataSnapshot.getValue(Car.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
        String fBrand = "BMW";
        String fModel = "9 Series Sedan";
        int page = 1;
        searchQueue = new LinkedList();
        createSearchQueue(fBrand,fModel,page);
        searchQueue.peek();


    }
}

