package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        CheckingInputs[] checkingInputs = defineElements(v);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(checkingInputs))
                    addCar(v);
            }
        });
        return v;
    }

    private void addCar(View v){

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // İLAN VERME BUTONUNA TIKLANDIGINDA GERCEKLESECEK OLAN ISLEMLER

            }
        });
    }

    private CheckingInputs[] defineElements(View v){
        CheckingInputs[] checkingInputs = {new CheckingInputs("Brand", brand = v.findViewById(R.id.advert_car_brand)),
                new CheckingInputs("Model", model = v.findViewById(R.id.advert_car_model)),
                new CheckingInputs("Color", color = v.findViewById(R.id.advert_car_color)),
                new CheckingInputs("Price", price = v.findViewById(R.id.advert_car_price)),
                new CheckingInputs("Detail", detail = v.findViewById(R.id.advert_car_description)),
        };

        btn_add = (Button)v.findViewById(R.id.btn_add);

        return checkingInputs;
    }

    private boolean isEmpty(CheckingInputs[] checkingInputs) {
        for (CheckingInputs input: checkingInputs) {
            if(input.getClass().getName().equalsIgnoreCase("EditText")){
                if (input.getEdText().getText().toString().matches("")) {
                    Toast.makeText(getContext(), "You did not enter the " + input.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            else{
                if (input.getTextInput().getEditText().getText().toString().matches("")) {
                    Toast.makeText(getContext(), "You did not enter the " + input.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }

        }
        return false;
    }
}