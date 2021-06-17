package com.example.RentalCars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.RentalCars.Entity.Car;

import java.util.ArrayList;

public class ListCarActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    // Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_car_recyclerView);
        // adapter =new Adapter(Car.getData(), this);
        // mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
    }

}