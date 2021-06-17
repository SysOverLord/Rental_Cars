package com.example.RentalCars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RentalCars.Entity.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class testForCarPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);
        Bundle extras = getIntent().getExtras();
        //String carId = extras.getString("carId");
        String carId = "e3b41480-794a-4d9e-b13b-db9cca713b7d";
        createCarPageInformation(carId);
    }

    public void createCarPageInformation(String carId){

        TextView textBrand = findViewById(R.id.car_details_txtViewCarBrand);
        TextView textModel = findViewById(R.id.car_details_txtViewCarModel);
        TextView textColor = findViewById(R.id.car_details_txtViewCarColor);
        TextView textPrice = findViewById(R.id.car_details_txtViewCarPrice);
        TextView textDesc = findViewById(R.id.car_details_txtViewCarDescription);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars" );
        Query query = myRef.orderByChild("carId").equalTo(carId);
        query.addValueEventListener(new ValueEventListener() {
            Car car = new Car();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    car = dataSnapshot.getValue(Car.class);
                }
                //Arabanın bilgi kutucuklarını değiştir
                textBrand.setText(car.getBrand());
                textModel.setText(car.getModel());
                textColor.setText(car.getColor());
                textPrice.setText(String.format("%s", car.getDailyPrice()));
                textDesc.setText(car.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}