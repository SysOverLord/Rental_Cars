package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

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

        defineElements(v);

        addCar(v);

        return v;
    }


    private void defineElements(View v){
        brand = (TextInputLayout)v.findViewById(R.id.advert_car_brand);
        model = (TextInputLayout)v.findViewById(R.id.advert_car_model);
        color = (TextInputLayout)v.findViewById(R.id.advert_car_color);
        price = (TextInputLayout)v.findViewById(R.id.advert_car_price);
        detail = (EditText)v.findViewById(R.id.advert_car_description);
        btn_add = (Button)v.findViewById(R.id.add);
    }

    private void addCar(View v){

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // İLAN VERME BUTONUNA TIKLANDIGINDA GERCEKLESECEK OLAN ISLEMLER



            }
        });
    }



}