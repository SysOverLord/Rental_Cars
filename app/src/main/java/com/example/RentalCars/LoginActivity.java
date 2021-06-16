package com.example.RentalCars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextInputLayout textInputLayoutUser = findViewById(R.id.login_username);
        TextInputLayout textInputLayoutPass = findViewById(R.id.login_password);
        Button button = findViewById(R.id.login);
        Button toRegBut = findViewById(R.id.signUpScreen);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        toRegBut.setOnClickListener(v -> {
            Intent register = new Intent(this, SignUp.class);
            startActivity(register);
        });


        button.setOnClickListener(v -> {
            String username = textInputLayoutUser.getEditText().getText().toString().trim();
            String pass = textInputLayoutPass.getEditText().getText().toString().trim();
            // public void loginMethod(String username,String password)
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef  = database.getReference("users");
            Query query = myRef.orderByChild("username").equalTo(username);
            if(username.equals("")){
                builder.setMessage("Username is empty");
                AlertDialog alert = builder.create();
                alert.setTitle("Notify");
                alert.show();
            }
            else{
                Intent mainPage = new Intent(this, MainPage.class);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String dbPass = "";
                        String userId = "";
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            dbPass = snapshot.child("password").getValue(String.class);
                            userId = snapshot.child("userId").getValue(String.class);
                        }
                        if(dbPass.equals("")){
                            builder.setMessage("User not found");
                            AlertDialog alert = builder.create();
                            alert.setTitle("Notify");
                            alert.show();
                        }
                        else if(dbPass.equals(pass.trim())){
                            builder.setMessage("Login Successful");
                            AlertDialog alert = builder.create();
                            alert.setTitle("Notify");
                            alert.show();
                            //Login olduk MainPageye götür
                            mainPage.putExtra("userId",userId);
                            startActivity(mainPage);
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