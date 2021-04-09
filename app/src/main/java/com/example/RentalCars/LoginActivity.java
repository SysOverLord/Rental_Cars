package com.example.RentalCars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        EditText editTextPass = findViewById(R.id.editTextPass);
        EditText editTextUser = findViewById(R.id.editTextUser);
        Button button = findViewById(R.id.button);
        Button toRegBut = findViewById(R.id.toRegSecBut);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        toRegBut.setOnClickListener(v -> {
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
        });


        button.setOnClickListener(v -> {

            String username = editTextUser.getText().toString().trim();
            String pass = editTextPass.getText().toString().trim();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef  = database.getReference("users");
            myRef = myRef.child(username);
            if(username.equals("")){
                builder.setMessage("Username is empty");
                AlertDialog alert = builder.create();
                alert.setTitle("Retard Alert");
                alert.show();
            }
            else{
                Intent main = new Intent(this, MainActivity.class);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String dbPass  = dataSnapshot.child("password").getValue(String.class);
                        if(!dataSnapshot.exists()){
                            builder.setMessage("SEN KİMSİN AMQ");
                            AlertDialog alert = builder.create();
                            alert.setTitle("Retard Alert");
                            alert.show();
                        }
                        else if(dbPass.equals(pass.trim())){
                            builder.setMessage("Giriş Başarılı");
                            AlertDialog alert = builder.create();
                            alert.setTitle("Notify");
                            alert.show();
                            startActivity(main);
                        }
                        else{
                            builder.setMessage("Wrong password");
                            AlertDialog alert = builder.create();
                            alert.setTitle("Notify");
                            alert.show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("ERROR", "Failed to read value.", error.toException());
                    }
                });
            }
        });


    }
}