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
    private void checkDates(Date startDate, Date endDate, String rentedCarId,Rental rent){
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
                        Date dbStart = dataSnapshot.child("startDate").getValue(Date.class);
                        Date dbEnd = dataSnapshot.child("endDate").getValue(Date.class);
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
        //float dailyPrice = extras.getInt("dailyPrice");
        float dailyPrice = 15f;
        String rentedCarId = "ec2503bb-32a5-41ad-94cd-de13bdc76fea";
        String renterId = "b4d5676a-e34d-4de8-9c63-2f4898abd9ba";
        Date startDate = new Date(2021,11,28);
        Date endDate = new Date(2021,11,27);

        if(startDate.compareTo(endDate) <= 0){
            int yearDiff = endDate.getYear() - startDate.getYear();
            int monthDiff = endDate.getMonth() - startDate.getMonth();
            int dayDiff = endDate.getDate() - startDate.getDate();
            float totalPrice = (yearDiff * 365 + monthDiff * 30 + dayDiff) * dailyPrice;


            Rental rent = new Rental(rentedCarId,startDate,endDate,totalPrice,renterId);
            isRentable = true;
            dbReturned = false;

            checkDates(startDate,endDate,rentedCarId,rent);
        }
        else{
            //Hata mesajı
            CircularProgressIndicator circular = findViewById(R.id.CPI);
            circular.setVisibility(View.INVISIBLE);
        }

    }
}