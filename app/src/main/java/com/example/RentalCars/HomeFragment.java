package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

        fBrand = brandSearch.getText().toString();
        fModel = modelSearch.getText().toString();
        fColor = colorSearch.getText().toString();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.home_fragment_recyclerView);

        createSearchQueue(fBrand,fModel,1,v);

        btn_carSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fBrand = brandSearch.getText().toString();
                fModel = modelSearch.getText().toString();
                fColor = colorSearch.getText().toString();
                createSearchQueue(fBrand,fModel,1,v);

            }
        });


        // Inflate the layout for this fragment
        return v;

    }



    private  void createSearchQueue(String fBrand,String fModel, int page,View v) {

        ArrayList<Car> carList = new ArrayList<Car>();
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
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (carList.size() < 5) {
                        carList.add(dataSnapshot.getValue(Car.class));
                    }

                }
                adapterCarRecycler = new AdapterCarRecycler(getActivity(),carList);
                mRecyclerView.setAdapter(adapterCarRecycler);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.addOnItemTouchListener(
                        new CarRecyclerItemClickListener(getActivity(), mRecyclerView, new CarRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(),testForCarPage.class);
                                intent.putExtra("car",carList.get(position));
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