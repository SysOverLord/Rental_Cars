package com.example.RentalCars;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Adapters.AdapterPerson;
import com.example.RentalCars.Entity.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowUsersActivity extends AppCompatActivity {
    private ArrayList<Person> arrayListPerson;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showusers);
        mListView = findViewById(R.id.listview_allUsers);
        getUsersFromDB();
    }

    public void getUsersFromDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/");
        Query query = myRef.orderByValue();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListPerson = new ArrayList<Person>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    arrayListPerson.add(dataSnapshot.getValue(Person.class));
                }
                AdapterPerson adapterPerson = new AdapterPerson(ShowUsersActivity.this, android.R.layout.simple_list_item_1,arrayListPerson);
                mListView.setAdapter(adapterPerson);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
