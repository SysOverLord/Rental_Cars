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
    private void showSearchQueue(Queue searchQueue){
        //Ekrana liste bastırma
    }

    private  void createSearchQueue(String fBrand,String fModel, int page) {

        Queue searchQueue = new LinkedList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/");
        Query query;
        // Filtreleme başlangıcı
        if(!fBrand.equals("") && !fModel.equals("")){
            query = myRef.orderByChild("BMF").equalTo(fBrand + "_" + fModel);
        }
        else if (!fModel.equals(""))
            query = myRef.orderByChild("model").equalTo(fModel);
        else if (!fBrand.equals(""))
            query = myRef.orderByChild("brand").equalTo(fBrand);
        else
            query = myRef.orderByValue();
        //Filtreleme bitişi
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (searchQueue.size() < 5) {
                        searchQueue.add(dataSnapshot.getValue(Car.class));
                    }

                }
                //Show SearchQueue
                showSearchQueue(searchQueue);
                searchQueue.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
        String fBrand = "";
        String fModel = "";
        int page = 1;
        //Tuşa bastığında bunu çağıracak
        createSearchQueue(fBrand,fModel,page);


    }
}

