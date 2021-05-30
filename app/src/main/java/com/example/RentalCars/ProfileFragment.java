package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Entity.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment{

    public void printPersonInf(Person person){
        // Ekrandaki textleri değiştir
    }
    public void createProfileInf(String userId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + userId);
        Person person = new Person();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                printPersonInf(person);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment_profile, container, false);
       Bundle args = getArguments();
       String userId = args.getString("userId");

       createProfileInf(userId);






       return null;
   }
}
