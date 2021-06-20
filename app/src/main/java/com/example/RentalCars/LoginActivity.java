package com.example.RentalCars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;


import com.example.RentalCars.Entity.DialogHelper;
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
        DialogHelper dialogHelper = DialogHelper.getInstance();


        toRegBut.setOnClickListener(v -> {
            Intent register = new Intent(this, SignUp.class);
            startActivity(register);
        });


        button.setOnClickListener(v -> {
            String username = textInputLayoutUser.getEditText().getText().toString().trim();
            String pass = textInputLayoutPass.getEditText().getText().toString().trim();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef  = database.getReference("users");
            Query query = myRef.orderByChild("username").equalTo(username);
            if(username.equals(""))
                dialogHelper.ShowMessage("Username is empty", this);
            else if(pass.equals(""))
                dialogHelper.ShowMessage("Password is empty", this);
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
                        if(userId.equals(""))
                            dialogHelper.ShowMessage("User not found", LoginActivity.this);
                        else if(dbPass.equals(pass.trim())){
                            dialogHelper.ShowMessage("Login Successful", LoginActivity.this);
                            //Login olduk MainPageye götür
                            mainPage.putExtra("userId",userId);
                            startActivity(mainPage);
                        }
                        else
                            dialogHelper.ShowMessage("Wrong password", LoginActivity.this);
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