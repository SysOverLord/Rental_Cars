package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RentalCars.AdapterListeners.CarRecyclerItemClickListener;
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
    EditText priceSearch;
    ImageButton btn_carSearch;
    String fBrand;
    String fModel;
    String fColor;
    RecyclerView mRecyclerView;
    AdapterCarRecycler adapterCarRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        defineElements(v);

        fBrand = brandSearch.getText().toString().toLowerCase();
        fModel = modelSearch.getText().toString().toLowerCase();
        fColor = colorSearch.getText().toString().toLowerCase();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_fragment_recyclerView);
        adapterCarRecycler = new AdapterCarRecycler(getActivity(),new ArrayList<Car>());
        String userId = getArguments().getString("userId");
        createSearchList(fBrand,fModel,1,v,userId);

        btn_carSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fBrand = brandSearch.getText().toString().toLowerCase();
                fModel = modelSearch.getText().toString().toLowerCase();
                fColor = colorSearch.getText().toString().toLowerCase();
                createSearchList(fBrand,fModel,1,v,userId);

            }
        });


        // Inflate the layout for this fragment
        return v;

    }



    private  void createSearchList(String fBrand,String fModel, int page,View v,String userId) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/");
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
                ArrayList<Car> carList = new ArrayList<Car>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (carList.size() < 5) {
                        Car temp = dataSnapshot.getValue(Car.class);
                        if( fColor.equals("") || temp.getColor().equals(fColor))
                            carList.add(temp);
                    }

                }
                adapterCarRecycler.setNewList(carList);
                mRecyclerView.setAdapter(adapterCarRecycler);


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.addOnItemTouchListener(
                        new CarRecyclerItemClickListener(getActivity(), mRecyclerView, new CarRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), ActivityCarPage.class);
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
        priceSearch = (EditText)v.findViewById(R.id.search_price_edittxt);
        btn_carSearch = (ImageButton)v.findViewById(R.id.btn_search_car);
    }
}