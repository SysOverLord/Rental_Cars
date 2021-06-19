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
import com.example.RentalCars.R;
import com.google.firebase.database.ThrowOnExtraProperties;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterCar extends ArrayAdapter<Car> {
    private static final String LOG_TAG = "CarListAdapter";
    private Activity activity;
    private ArrayList<Car> lCar;
    private static LayoutInflater inflater = null;


    public AdapterCar(Activity activity,int textViewResourceId,ArrayList<Car> _lCar){
        super(activity,textViewResourceId,_lCar);
        try{
            this.activity = activity;
            this.lCar = _lCar;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){
            Log.e(LOG_TAG, "Construction error", e);
        }
    }

    public int getCount() {
        return lCar.size();
    }
    public Car getItem(Car position) {
        return position;
    }

    public void setCarList(ArrayList<Car> carList){
        lCar = carList;
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



            holder.display_brand.setText(lCar.get(position).getBrand());
            holder.display_model.setText(lCar.get(position).getModel());
            holder.display_color.setText(lCar.get(position).getColor());
            holder.display_dailyPrice.setText(String.valueOf(lCar.get(position).getDailyPrice()));

        } catch (Exception e) {


        }
        return vi;
    }
}