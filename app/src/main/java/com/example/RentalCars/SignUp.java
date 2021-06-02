package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Entity.Address;
import com.example.RentalCars.Entity.CreditCard;
import com.example.RentalCars.Entity.Person;
import com.example.RentalCars.Entity.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    TextInputLayout firstName;
    TextInputLayout lastName;
    TextInputLayout username;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout city;
    TextInputLayout district;
    TextInputLayout street;
    TextInputLayout no;
    TextInputLayout cardName;
    TextInputLayout cardNo;
    TextInputLayout cardCvv;
    Button btn_call_login;
    Button btn_signup;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        CheckingInputs[] checkingInputs = defineElements();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(checkingInputs)){
                    String newId = UUID.randomUUID().toString();
                    User user = new User(username.getEditText().getText().toString(),
                            email.getEditText().getText().toString(),
                            password.getEditText().getText().toString(), newId);
                    Address address = new Address(city.getEditText().getText().toString(),
                            district.getEditText().getText().toString(), street.getEditText().getText().toString(),
                            no.getEditText().getText().toString());
                    //EXPİRE DATE NULL OLMAYACAK DEĞİŞTİR ONU
                    CreditCard creditCard = new CreditCard(null,cardCvv.getEditText().getText().toString()
                            ,cardNo.getEditText().getText().toString());
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    Date registerDate = new Date();
                    Person person = new Person(user,firstName.getEditText().getText().toString(),
                            lastName.getEditText().getText().toString(),address, creditCard,registerDate);



                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef  = database.getReference("users");
                    Query query = myRef.orderByChild("username").equalTo(user.getUsername());
                    myRef = myRef.child(newId);


                    if(user.getUsername().equals("")){
                        builder.setMessage("Username is empty");
                        AlertDialog alert = builder.create();
                        alert.setTitle("Notify");
                        alert.show();
                    }

                    else{
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()){
                                    myRef.setValue(person);
                                    builder.setMessage("User created.");
                                    AlertDialog alert = builder.create();
                                    alert.setTitle("Notify");
                                    alert.show();
                                    // Üyeliğine girmiş gibi düşün ona göre çağır
                                }
                                else {
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
                    }
                }
            }
        });


        btn_call_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.RentalCars.SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    private CheckingInputs[] defineElements(){
        CheckingInputs[] checkingInputs = {new CheckingInputs("FirstName", firstName = (TextInputLayout) findViewById(R.id.firstName)),
                                            new CheckingInputs("LastName", lastName = (TextInputLayout) findViewById(R.id.lastName)),
                                            new CheckingInputs("Username", username = (TextInputLayout) findViewById(R.id.userName)),
                                            new CheckingInputs("Email", email = (TextInputLayout) findViewById(R.id.email)),
                                            new CheckingInputs("Password", password = (TextInputLayout) findViewById(R.id.password)),
                                            new CheckingInputs("City", city = (TextInputLayout) findViewById(R.id.city)),
                                            new CheckingInputs("District", district = (TextInputLayout) findViewById(R.id.district)),
                                            new CheckingInputs("Street", street = (TextInputLayout) findViewById(R.id.street)),
                                            new CheckingInputs("AddressNo", no = (TextInputLayout) findViewById(R.id.addressNo)),
                                            new CheckingInputs("CardName", cardName = (TextInputLayout) findViewById(R.id.cardName)),
                                            new CheckingInputs("CardNumber", cardNo = (TextInputLayout) findViewById(R.id.cardNumber)),
                                            new CheckingInputs("CardCvv", cardCvv = (TextInputLayout) findViewById(R.id.cardCvv)),
        };

        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_call_login = (Button)findViewById(R.id.btn_login_screen);

        return checkingInputs;
    }

    private boolean isEmpty(CheckingInputs[] checkingInputs) {
        for (CheckingInputs input: checkingInputs) {
            if (input.getTextInput().getEditText().getText().toString().matches("")) {
                Toast.makeText(this, "You did not enter the " + input.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}