package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment {

    EditText brandSearch;
    EditText modelSearch;
    EditText colorSearch;
    EditText priceSearch;
    ImageButton btn_carSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

       defineElements(v);

        btn_carSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ARABA SEARCH İŞLEMİ

            }
        });

        return v;
    }

    private void defineElements(View v){
        brandSearch = (EditText)v.findViewById(R.id.search_brand_edittxt);
        modelSearch = (EditText)v.findViewById(R.id.search_model_edittxt);
        colorSearch = (EditText)v.findViewById(R.id.search_color_edittxt);
        priceSearch = (EditText)v.findViewById(R.id.search_price_edittxt);
        btn_carSearch = (ImageButton)v.findViewById(R.id.btn_search_car);
    }

}