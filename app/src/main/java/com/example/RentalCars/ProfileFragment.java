package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;


public class ProfileFragment extends Fragment {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView username;
    private TextView creditLimit;
    private TextView registerDate;
    private TextInputLayout newEmail;
    private TextInputLayout newPassword;
    private Button btn_save;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        defineElements(v);

        saveChanges(v);

        return v;
    }

    private void defineElements(View v){
        firstName = (TextView)v.findViewById(R.id.prf_firstName);
        lastName = (TextView)v.findViewById(R.id.prf_lastName);
        email = (TextView)v.findViewById(R.id.prf_email);
        username = (TextView)v.findViewById(R.id.prf_username);
        creditLimit = (TextView)v.findViewById(R.id.prf_cardLimit);
        registerDate = (TextView)v.findViewById(R.id.prf_register_time);
        newEmail = v.findViewById(R.id.prf_newMail);
        newPassword =v.findViewById(R.id.prf_newPassword);
        btn_save = (Button)v.findViewById(R.id.btn_save);
    }

    private void saveChanges(View v){

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // KAYDET BUTONUNA TIKLANDIGINDA GERCEKLESECEK OLAN ISLEMLER

            }
        });

    }

}