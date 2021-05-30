package com.example.RentalCars;

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

public class ShowUserCarsActivity  extends AppCompatActivity {
    public void createCarList(String userId){
        Queue carList = new LinkedList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars" );
        Query query = myRef.orderByChild("ownerId").equalTo(userId);
        //BMF = B_M


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    carList.add(dataSnapshot.getValue(Car.class));
                }
                //show car list
                carList.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        //String userId = extras.getString("userId");
        String userId  = "5276eb86-5265-463d-a2ec-60aea1a4bdcc";
        createCarList(userId);

    }
}
