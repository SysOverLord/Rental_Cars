package com.example.RentalCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class MyCarsFragment extends Fragment {

    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private String[] myCars = {"CAR1", "CAR2", "CAR3", "CAR4", "CAR5",
            "CAR6", "CAR7", "CAR8", "CAR9", "CAR10", "CAR11", "CAR12", "CAR13"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_my_cars, container, false);

        mListView = (ListView)v.findViewById(R.id.listview_mycars);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myCars );
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }
        });

        return v;
    }

}