package com.example.RentalCars;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Entity.Rental;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class RentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageButton btn_startDate;
    ImageButton btn_endDate;
    TextView startDateText;
    TextView endDateText;
    Button btn_rental_complete;


    Date startDate;
    Date endDate;


    String selectedDate;

    boolean isRentable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        defineElements();


        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = "startDate";
                showStartDate();
            }
        });

        btn_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = "endDate";
                showEndDate();
            }
        });

        btn_rental_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                String rentedCarId = extras.getString("rentedCarId");
                String renterId = extras.getString("renterId");
                String carOwnerId = extras.getString("carOwnerId");
                float dailyPrice = extras.getFloat("dailyPrice");


                if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    //24 hours 60 minutes 60 seconds 1000 milliseconds
                    float totalPrice = (dayDiff + 1) * dailyPrice;


                    Rental rent = new Rental(rentedCarId, startDate, endDate, totalPrice, renterId,carOwnerId);
                    isRentable = true;
                    getRenterFullName(rent);

                } else {
                    //Hata mesajı
                    Toast.makeText(getApplicationContext(), "Illegal Date", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }
    private void showStartDate(){
        DatePickerDialog startDatePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        startDatePickerDialog.show();
    }

    private void showEndDate(){
        DatePickerDialog endDatePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        endDatePickerDialog.show();
    }


    private void defineElements(){
        btn_startDate = (ImageButton)findViewById(R.id.rental_start_date);
        btn_endDate = (ImageButton)findViewById(R.id.rental_end_date);
        startDateText = (TextView)findViewById(R.id.txtview_start_date);
        endDateText = (TextView)findViewById(R.id.txtview_end_date);
        btn_rental_complete = (Button)findViewById(R.id.btn_rental_complete);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) +"/" + year;
        if(selectedDate.equals("startDate")){
            startDateText.setText(date);
            startDate = new Date(year - 1900,month +1 ,dayOfMonth);
        }
        //Year - 1900 düzgün zamanı atması için öbür türlü 1900 yıl fazla atıyor
        //Datenin işleyişine göre 121 değerinde olması gerekirken 2021 oluyor
        //Sonuç olarak da kiralamaları listelerken 3921 yılında olduğunu gösteriyor.
        else{
            endDateText.setText(date);
            endDate = new Date(year - 1900,month +1 ,dayOfMonth);
        }

    }

    private void getRenterFullName(Rental rent){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users/" + rent.getRenterId());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    rent.setRenterFullName(String.format("%s %s"
                            ,snapshot.child("firstName").getValue(String.class)
                            ,snapshot.child("lastName").getValue(String.class)));
                    checkDates(startDate,endDate, rent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkLimit(Rental rent){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users/" + rent.getRenterId() + "/creditCard");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float dbLimit = snapshot.child("limit").getValue(Float.class);
                float totalPrice = rent.getTotalPrice();
                if(totalPrice <= dbLimit){
                    userRef.child("limit").setValue(dbLimit - totalPrice);
                    transactionToOwner(rent);

                }
                else {
                    //Yetersiz Limit
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void transactionToOwner(Rental rent){
        String carOwnerId = rent.getCarOwnerId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" +  carOwnerId + "/creditCard");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float ownerLimit = snapshot.child("limit").getValue(Float.class);
                myRef.child("limit").setValue(ownerLimit + rent.getTotalPrice());
                rentCar(rent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void rentCar(Rental rent){
        String uuid = UUID.randomUUID().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rentals/" + uuid );
        Intent mainPage = new Intent(this,MainPage.class);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Save rent information to database
                myRef.setValue(rent);
                //UserId for further other operations
                mainPage.putExtra("userId",rent.getRenterId());
                //Going to mainPage
                startActivity(mainPage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkDates(Date startDate, Date endDate,Rental rent){
        //CircularProgressIndicator circular = findViewById(R.id.CPI);
        //circular.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rentals/" );
        Query query = myRef.orderByChild("rentedCarId").equalTo(rent.getRentedCarId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Date dbStart = dataSnapshot.child("startDate").getValue(Date.class);
                        Date dbEnd = dataSnapshot.child("endDate").getValue(Date.class);
                        if((dbStart.compareTo(endDate) <= 0 && dbEnd.compareTo(endDate) >= 0)
                                || (dbStart.compareTo(startDate) <= 0 && dbEnd.compareTo(startDate) >= 0)
                                ||(dbStart.compareTo(startDate) >= 0 && dbEnd.compareTo(endDate) <= 0)){
                            isRentable = false;
                            break;
                        }


                    }
                }

                if (isRentable){
                    checkLimit(rent);
                }

                //circular.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}