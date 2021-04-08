package com.example.RentalCars;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference myRef;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText userText  = findViewById(R.id.editTextRegUser);
        EditText emailText = findViewById(R.id.editTextRegEmail);
        EditText passText = findViewById(R.id.editTextRegPass);

        Button regButton = findViewById(R.id.regısterButt);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        regButton.setOnClickListener(v -> {


            User user = new User(userText.getText().toString(),
                    emailText.getText().toString(), passText.getText().toString());


            FirebaseDatabase database = FirebaseDatabase.getInstance();
             myRef  = database.getReference("users");
            myRef = myRef.child(userText.getText().toString());


            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String email= dataSnapshot.child("email").getValue(String.class);
                    if(username == null){
                        builder.setMessage("User created.");
                        AlertDialog alert = builder.create();
                        alert.setTitle("Notify");
                        alert.show();
                    }

                    else if(username.equals(userText.getText().toString())){
                        User oldUser = new User(username,email,password);
                        myRef.setValue(oldUser);
                        builder.setMessage("User has already created.");
                        AlertDialog alert = builder.create();
                        alert.setTitle("Notify");
                        alert.show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                myRef.setValue(user);

        });


    }
}
