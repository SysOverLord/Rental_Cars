package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.CheckingInputs;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AdvertFragment extends Fragment {

    private TextInputLayout brand;
    private TextInputLayout model;
    private TextInputLayout color;
    private TextInputLayout price;
    private EditText detail;
    private Button btn_add;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_advert, container, false);
        String userId = getArguments().getString("userId");

        CheckingInputs[] checkingInputs = defineElements(v);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(!isEmpty(checkingInputs))
                addCar(v, checkingInputs, userId);
            }
        });
        return v;
    }

    private void addCar(View v, CheckingInputs[] checkingInputs, String userId) {

        String carId = UUID.randomUUID().toString();

        float dailyPrice = Float.parseFloat(checkingInputs[3].getTextInput().getEditText().getText().toString());
        //DESC KISMI BOZUK DÜZELTELİM
        Car newCar = new Car(checkingInputs[0].getTextInput().getEditText().getText().toString(),
                checkingInputs[1].getTextInput().getEditText().getText().toString(),
                checkingInputs[2].getTextInput().getEditText().getText().toString(),
                dailyPrice, null
                , carId, userId);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + newCar.getCarId());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myRef.setValue(newCar);
                //BMF STANDS FOR BRAND MODEL FILTER
                myRef.child("BMF").setValue(newCar.getBrand() + "_" + newCar.getModel());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private CheckingInputs[] defineElements(View v) {
        CheckingInputs[] checkingInputs = {new CheckingInputs("Brand", brand = (TextInputLayout) v.findViewById(R.id.advert_car_brand)),
                new CheckingInputs("Model", model = (TextInputLayout) v.findViewById(R.id.advert_car_model)),
                new CheckingInputs("Color", color = (TextInputLayout) v.findViewById(R.id.advert_car_color)),
                new CheckingInputs("Price", price = (TextInputLayout) v.findViewById(R.id.advert_car_price)),
                new CheckingInputs("Detail", detail = (EditText) v.findViewById(R.id.advert_car_description)),
        };

        btn_add = (Button) v.findViewById(R.id.btn_add);

        return checkingInputs;
    }

    private boolean isEmpty(CheckingInputs[] checkingInputs) {
        for (CheckingInputs input : checkingInputs) {
            if (input.getClass().getName().equalsIgnoreCase("EditText")) {
                if (input.getEdText().getText().toString().matches("")) {
                    Toast.makeText(getContext(), "You did not enter the " + input.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            } else {
                if (input.getTextInput().getEditText().getText().toString().matches("")) {
                    Toast.makeText(getContext(), "You did not enter the " + input.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }

        }
        return false;
    }
}