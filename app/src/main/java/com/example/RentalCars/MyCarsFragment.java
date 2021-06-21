package com.example.RentalCars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RentalCars.AdapterListeners.CarRecyclerItemClickListener;
import com.example.RentalCars.Adapters.AdapterCar;
import com.example.RentalCars.Adapters.AdapterCarRecycler;
import com.example.RentalCars.Entity.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyCarsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AdapterCarRecycler adapterCarRecycler;

    public void createCarList(String userId,View v,Activity activity){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars" );
        Query query = myRef.orderByChild("ownerId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Car> carList = new ArrayList<Car>();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    carList.add(dataSnapshot.getValue(Car.class));
                }

                mRecyclerView = (RecyclerView) v.findViewById(R.id.mycars_fragment_recyclerview);
                adapterCarRecycler = new AdapterCarRecycler(activity,carList);
                mRecyclerView.setAdapter(adapterCarRecycler);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.addOnItemTouchListener(
                        new CarRecyclerItemClickListener(activity, mRecyclerView, new CarRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(activity, ActivityCarPage.class);
                                intent.putExtra("userId",userId);
                                intent.putExtra("car",carList.get(position));
                                intent.putExtra("pageType","myCarPage");
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
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
        Activity activity = getActivity();
        Bundle extras = getArguments();
        String userId = extras.getString("userId");
        createCarList(userId,v,activity);

        return v;
    }
}