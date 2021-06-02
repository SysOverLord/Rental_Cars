package com.example.RentalCars.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.R;

import java.util.ArrayList;

public class AdapterCar extends ArrayAdapter {
    private Activity activity;
    private ArrayList<Car> lCar;
    private static LayoutInflater inflater = null;

    public AdapterCar (Activity activity, int textViewResourceId,ArrayList<Car> _lCar) {
        super(activity, textViewResourceId);
        try {
            this.activity = activity;
            this.lCar = _lCar;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lCar.size();
    }


    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_number;

    }
}
