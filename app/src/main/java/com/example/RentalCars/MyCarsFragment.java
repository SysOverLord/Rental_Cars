package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Adapters.AdapterCar;
import com.example.RentalCars.Entity.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MyCarsFragment extends Fragment {

    private ListView mListView;
    private AdapterCar adapterCar;


    public void createCarList(String userId,View v){
        ArrayList<Car> carList = new ArrayList<Car>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars" );
        Query query = myRef.orderByChild("ownerId").equalTo(userId);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    carList.add(dataSnapshot.getValue(Car.class));
                }
                //show car list
                mListView = (ListView)v.findViewById(R.id.listview_mycars);
                //adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,myCars);
                adapterCar = new AdapterCar(getActivity(), android.R.layout.simple_list_item_1,carList);
                mListView.setAdapter(adapterCar);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Açıklama boş olursa crash yiyoz
                        Intent intent = new Intent(getActivity(),testForCarPage.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("car",carList.get(position));
                        startActivity(intent);

                    }
                });
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


        View v =  inflater.inflate(R.layout.fragment_my_cars, container, false);

        Bundle extras = getArguments();
        String userId = extras.getString("userId");
        createCarList(userId,v);




        return v;
    }

}