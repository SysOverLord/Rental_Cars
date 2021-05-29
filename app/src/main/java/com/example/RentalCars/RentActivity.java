package com.example.RentalCars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.RentalCars.Entity.Rental;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class RentActivity extends AppCompatActivity {
    boolean isRentable;
    boolean dbReturned;
    private void rentCar(Rental rent){
        String uuid = UUID.randomUUID().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rentals/" + uuid );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.setValue(rent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkDates(Calendar startDate, Calendar endDate, String rentedCarId,Rental rent){
        CircularProgressIndicator circular = findViewById(R.id.CPI);
        circular.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rentals/" );
        Query query = myRef.orderByChild("rentedCarId").equalTo(rentedCarId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbReturned = true;
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Calendar dbStart = dataSnapshot.child("startDate").getValue(Calendar.class);
                        Calendar dbEnd = dataSnapshot.child("endDate").getValue(Calendar.class);
                        if(dbEnd.compareTo(startDate) >= 0 && dbEnd.compareTo(endDate) <= 0){
                            isRentable = false;
                            break;
                        }

                        else if (dbStart.compareTo(startDate) >= 0 && dbStart.compareTo(endDate) <= 0){
                            isRentable = false;
                            break;
                        }


                    }
                }
                if (isRentable)
                    rentCar(rent);
                circular.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        Bundle extras = getIntent().getExtras();
        //String rentedCarId = extras.getString("rentedCarId");
        //String renterId = extras.getString("renterId");
        String rentedCarId = "096ba1f4-15a9-4344-8fcc-074b33c46203";
        String renterId = "5276eb86-5265-463d-a2ec-60aea1a4bdcc";
        Calendar startDate = Calendar.getInstance();
        startDate.set(2021,11,25);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2021,11,27);
        float totalPrice = 0.0f;


        Rental rent = new Rental(rentedCarId,startDate,endDate,totalPrice,renterId);
        isRentable = true;
        dbReturned = false;

        checkDates(startDate,endDate,rentedCarId,rent);
        String a = "";
    }
}