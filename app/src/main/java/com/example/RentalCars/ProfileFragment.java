package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Entity.Person;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
        String userId = getArguments().getString("userId");
        createProfileInf(userId);
        saveChanges(v);

        return v;
    }

    public void createProfileInf(String userId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");
        Query query = myRef.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Person person = dataSnapshot.getValue(Person.class);

                    firstName.setText(person.getFirstName());
                    lastName.setText(person.getLastName());
                    email.setText(person.getEmail());
                    username.setText(person.getUsername());
                    creditLimit.setText(Float.toString(person.getCreditCard().getLimit()));
                    //registerDate.setText(person.getFirstName());

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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