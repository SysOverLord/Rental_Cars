package com.example.RentalCars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText editTextPass = findViewById(R.id.editTextPass);
        EditText editTextUser = findViewById(R.id.editTextUser);
        Button button = findViewById(R.id.button);
        Button toRegBut = findViewById(R.id.toRegSecBut);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String username = editTextUser.getText().toString();
        String pass = editTextPass.getText().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference("users");


        toRegBut.setOnClickListener(v -> {
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
        });
        button.setOnClickListener(v -> {
           if(!myRef.child(username).equals(null) && myRef.child(username).child("password").equals(pass)){
               builder.setMessage("Giriş Başarılı");
               AlertDialog alert = builder.create();
               alert.setTitle("Notify");
               alert.show();
           }
           else{
                builder.setMessage("SEN KİMSİN AMQ");
                AlertDialog alert = builder.create();
                alert.setTitle("Retard Alert");
                alert.show();
           }
        });


    }
}