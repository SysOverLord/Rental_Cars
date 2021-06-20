package com.example.RentalCars;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Adapters.AdapterRentals;
import com.example.RentalCars.Entity.Rental;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowPreviousRentalsActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Rental> rentalList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rental_history);
        String carId = getIntent().getExtras().getString("carId");
        createCurrentCarRentalsList(carId);
    }






    public void createCurrentCarRentalsList(String carId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rentals");
        Query query = myRef.orderByChild("rentedCarId").equalTo(carId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                rentalList =new ArrayList<Rental>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    rentalList.add(dataSnapshot.getValue(Rental.class));
                }
                mListView = findViewById(R.id.listview_rental_history);
                AdapterRentals adapterRentals = new AdapterRentals(ShowPreviousRentalsActivity.this,android.R.layout.simple_list_item_1,rentalList);
                mListView.setAdapter(adapterRentals);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}
