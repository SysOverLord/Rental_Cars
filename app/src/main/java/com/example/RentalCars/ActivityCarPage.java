package com.example.RentalCars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.Rental;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.UUID;

public class ActivityCarPage extends AppCompatActivity {

    boolean isRentable;
    boolean dbReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras().getString("pageType").equals("myCarPage")){
            setContentView(R.layout.my_car_details);
            Bundle extras = getIntent().getExtras();
            Car car = (Car) extras.get("car");
            createCarPageInformation(car);
            ImageButton imageButton = findViewById(R.id.btn_delete_car);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCar(car.getCarId());
                }
            });
        }

        else{
            setContentView(R.layout.advertisement_car_details);
            Bundle extras = getIntent().getExtras();
            //String carId = extras.getString("carId");
            //String carId = "e3b41480-794a-4d9e-b13b-db9cca713b7d";
            String userId = extras.getString("userId");
            Car car = (Car) extras.get("car");
            createCarPageInformation(car);
            Button button = findViewById(R.id.btn_rent_car);
            Intent rentIntent = new Intent(this,RentActivity.class);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  rentIntent.putExtra("renterId",userId);
                  rentIntent.putExtra("rentedCarId",car.getCarId());
                  rentIntent.putExtra("dailyPrice",car.getDailyPrice());
                  rentIntent.putExtra("carOwnerId",car.getOwnerId());
                  startActivity(rentIntent);


                }
            });
        }

    }




    public void deleteCar(String carId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + carId );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.setValue(null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createCarPageInformation(Car car){

        TextView textBrand = findViewById(R.id.txtview_brand);
        TextView textModel = findViewById(R.id.txtview_model);
        TextView textColor = findViewById(R.id.txtview_color);
        TextView textPrice = findViewById(R.id.txtview_dailyprice);
        TextView textDesc = findViewById(R.id.txtview_desc);
        textBrand.setText(car.getBrand());
        textModel.setText(car.getModel());
        textColor.setText(car.getColor());
        textPrice.setText(String.format("%s", car.getDailyPrice()));
        textDesc.setText(car.getDesc());



    }

    public void createCarPageInformationFromDB(String carId){

        TextView textBrand = findViewById(R.id.txtview_brand);
        TextView textModel = findViewById(R.id.txtview_model);
        TextView textColor = findViewById(R.id.txtview_color);
        TextView textPrice = findViewById(R.id.txtview_dailyprice);
        TextView textDesc = findViewById(R.id.txtview_desc);

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