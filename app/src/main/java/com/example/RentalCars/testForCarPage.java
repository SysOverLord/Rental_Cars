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

public class testForCarPage extends AppCompatActivity {

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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rentProcess(car.getCarId(),userId,car.getDailyPrice());


                }
            });
        }

    }

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
        //CircularProgressIndicator circular = findViewById(R.id.CPI);
        //circular.setVisibility(View.VISIBLE);
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
                //circular.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void rentProcess(String rentedCarId,String renterId,float dailyPrice){
        Date startDate = new Date(2021,11,25);
        Date endDate = new Date(2021,11,27);

        if(startDate.compareTo(endDate) <= 0){
            int yearDiff = endDate.getYear() - startDate.getYear();
            int monthDiff = endDate.getMonth() - startDate.getMonth();
            int dayDiff = endDate.getDate() - startDate.getDate();
            float totalPrice = (yearDiff * 365 + monthDiff * 30 + dayDiff + 1) * dailyPrice;


            Rental rent = new Rental(rentedCarId,startDate,endDate,totalPrice,renterId);
            isRentable = true;
            dbReturned = false;

            checkDates(startDate,endDate,rentedCarId,rent);
        }
        else{
            //Hata mesajı
            Toast.makeText(getApplicationContext(), "Illegal Date", Toast.LENGTH_SHORT).show();
            //CircularProgressIndicator circular = findViewById(R.id.CPI);
            //circular.setVisibility(View.INVISIBLE);
        }
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