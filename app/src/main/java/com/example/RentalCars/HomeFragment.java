package com.example.RentalCars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class HomeFragment extends Fragment {
    EditText brandSearch;
    EditText modelSearch;
    EditText colorSearch;
    EditText price1Search;
    EditText price2Search;
    ImageButton btn_carSearch;
    String fBrand;
    String fModel;
    String fColor;
    RecyclerView mRecyclerView;
    Activity activity;
    ArrayList<Car> carList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        defineElements(v);

        fBrand = brandSearch.getText().toString().toLowerCase().trim();
        fModel = modelSearch.getText().toString().toLowerCase().trim();
        fColor = colorSearch.getText().toString().toLowerCase().trim();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_fragment_recyclerView);
        AdapterCarRecycler adapterCarRecycler = new AdapterCarRecycler(getActivity(),new ArrayList<Car>());
        activity = getActivity();
        String userId = getArguments().getString("userId");
        createSearchList(fBrand, fModel, userId);

        btn_carSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fBrand = brandSearch.getText().toString().toLowerCase().trim();
                fModel = modelSearch.getText().toString().toLowerCase().trim();
                fColor = colorSearch.getText().toString().toLowerCase().trim();
                createSearchList(fBrand, fModel, userId);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    private  void createSearchList(String fBrand, String fModel, String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/");
        float num1 = 0;
        float num2 = 0;
        if(!price1Search.getText().toString().equals(""))
            num1 = Float.parseFloat(price1Search.getText().toString());
        if(!price2Search.getText().toString().equals(""))
            num2 = Float.parseFloat(price2Search.getText().toString());
        float upperbound = Math.max(num1,num2);
        float lowerbound = Math.min(num1,num2);
        Query query;

        // Filtreleme başlangıcı
        if(!fBrand.equals("") && !fModel.equals("")){
            query = myRef.orderByChild("BMF").equalTo(fBrand + "_" + fModel);
        }
        else if (!fModel.equals(""))
            query = myRef.orderByChild("model").equalTo(fModel);
        else if (!fBrand.equals(""))
            query = myRef.orderByChild("brand").equalTo(fBrand);
        else
            query = myRef.orderByValue();

        //Filtreleme bitişi
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carList = new ArrayList<Car>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Car temp = dataSnapshot.getValue(Car.class);
                    if(( upperbound == 0 && lowerbound == 0 || (temp.getDailyPrice() >= lowerbound && temp.getDailyPrice() <= upperbound))
                            && (fColor.equals("") || temp.getColor().contains(fColor)))
                        carList.add(temp);
                }

                AdapterCarRecycler adapterCarRecycler = new AdapterCarRecycler(activity,carList);
                mRecyclerView.setAdapter(adapterCarRecycler);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.addOnItemTouchListener(
                        new CarRecyclerItemClickListener(activity, mRecyclerView, new CarRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(activity, ActivityCarPage.class);
                                intent.putExtra("car",carList.get(position));
                                intent.putExtra("pageType","rentPage");
                                intent.putExtra("userId",userId);
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

    private void defineElements(View v){
        brandSearch = (EditText)v.findViewById(R.id.search_brand_edittxt);
        modelSearch = (EditText)v.findViewById(R.id.search_model_edittxt);
        colorSearch = (EditText)v.findViewById(R.id.search_color_edittxt);
        price1Search = (EditText)v.findViewById(R.id.search_price1_edittxt);
        price2Search = (EditText)v.findViewById(R.id.search_price2_edittxt);
        btn_carSearch = (ImageButton)v.findViewById(R.id.btn_search_car);
    }
}