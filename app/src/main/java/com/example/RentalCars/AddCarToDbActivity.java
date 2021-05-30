package com.example.RentalCars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.RentalCars.Entity.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddCarToDbActivity extends AppCompatActivity {

    private void addToDB(Car newCar){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + newCar.getCarId());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myRef.setValue(newCar);
                myRef.child("BMF").setValue(newCar.getBrand() + "_" + newCar.getModel());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_add_car_to_db);
        String brand = "BMW";
        String model = "9 Series Sedan";
        String color = "orange";
        float dailyPrice = 25.1f;
        String desc = "-";
        String carId = UUID.randomUUID().toString();
        //String ownerId = extras.getString("ownerId");
        String ownerId = "5276eb86-5265-463d-a2ec-60aea1a4bdcc";
        Car newCar = new Car(brand,model,color,dailyPrice,desc,carId,ownerId);
        addToDB(newCar);

    }
}