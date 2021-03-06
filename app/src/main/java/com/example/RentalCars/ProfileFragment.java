package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Entity.DialogHelper;
import com.example.RentalCars.Entity.Person;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


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
    private Button btn_showUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        defineElements(v);
        String userId = getArguments().getString("userId");
        createProfileInf(userId);
        if(userId.equals("admin")){
            btn_showUsers.setVisibility(View.VISIBLE);
            btn_showUsers.setClickable(true);
        }
        else
            btn_showUsers.setClickable(false);


        btn_showUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ShowUsersActivity.class);
                startActivity(intent);
            }
        });
        saveChanges(v,userId);
        return v;
    }

    public void createProfileInf(String userId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);

                firstName.setText("First Name: " + person.getFirstName());
                lastName.setText("Last Name: " + person.getLastName());
                email.setText("Email: " + person.getEmail());
                username.setText("Username: " + person.getUsername());
                creditLimit.setText("Card Limit: " + Float.toString(person.getCreditCard().getLimit()));
                registerDate.setText("Register Date: " + new SimpleDateFormat("dd/MM/yyyy").format(person.getRegisterDate()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void defineElements(View v){
        firstName = v.findViewById(R.id.prf_firstName);
        lastName = v.findViewById(R.id.prf_lastName);
        email = v.findViewById(R.id.prf_email);
        username = v.findViewById(R.id.prf_username);
        creditLimit = v.findViewById(R.id.prf_cardLimit);
        registerDate = v.findViewById(R.id.prf_register_time);
        newEmail = v.findViewById(R.id.prf_newMail);
        newPassword =v.findViewById(R.id.prf_newPassword);
        btn_save = v.findViewById(R.id.btn_save);
        btn_showUsers = v.findViewById(R.id.btn_show_users);
    }

    private void saveChanges(View v,String userId){
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users/" + userId);
                    String newEmailStr = newEmail.getEditText().getText().toString();
                    String newPasswordStr = newPassword.getEditText().getText().toString();
                DialogHelper dialogHelper = DialogHelper.getInstance();

                    if(newEmailStr.equals(""))
                        dialogHelper.ShowMessage("New mail is empty.", getContext());
                    else if(newPasswordStr.equals(""))
                        dialogHelper.ShowMessage("New password is empty.", getContext());
                    else {
                        if (!newEmailStr.equals("")) {
                            myRef.child("email").setValue(newEmailStr);
                        }
                        if (!newPasswordStr.equals(""))
                            myRef.child("password").setValue(newPasswordStr);
                    }
            }
        });
    }
}