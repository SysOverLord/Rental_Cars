package com.example.RentalCars.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.Rental;
import com.example.RentalCars.R;
import com.google.firebase.database.ThrowOnExtraProperties;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterRentals extends ArrayAdapter<Rental> {
    private static final String LOG_TAG = "RentalListAdapter";
    private Activity activity;
    private ArrayList<Rental> _lRental;
    private static LayoutInflater inflater = null;


    public AdapterRentals(Activity activity,int textViewResourceId,ArrayList<Rental> _lRental){
        super(activity,textViewResourceId,_lRental);
        try{
            this.activity = activity;
            this._lRental = _lRental;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){
            Log.e(LOG_TAG, "Construction error", e);
        }
    }

    public int getCount() {
        return _lRental.size();
    }
    public Rental getItem(Rental position) {
        return position;
    }

    public void setCarList(ArrayList<Rental> RentalList){
        _lRental = RentalList;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_brand;
        public TextView display_model;
        public TextView display_color;
        public TextView display_dailyPrice;

    }
/*
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                //Layout Belirlenince hallediyok.
                vi = inflater.inflate(R.layout.car_item, null);
                holder = new ViewHolder();

                holder.display_brand = (TextView) vi.findViewById(R.id.car_item_txtViewCarBrand);
                holder.display_model = (TextView) vi.findViewById(R.id.car_item_txtViewCarModel);
                holder.display_color = (TextView) vi.findViewById(R.id.car_item_txtViewCarColor);
                holder.display_dailyPrice = (TextView) vi.findViewById(R.id.car_item_txtViewCarPrice);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_brand.setText(lRental.get(position).getStartDate());
            holder.display_model.setText(lRental.get(position).getEndDate());
            holder.display_color.setText(lRental.get(position).get());
            holder.display_dailyPrice.setText(String.valueOf(lRental.get(position).getDailyPrice()));

        } catch (Exception e) {


        }
        return vi;
    }*/
}
