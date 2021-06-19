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

import java.text.SimpleDateFormat;
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

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_renter;
        public TextView display_date;
        public TextView display_totalPrice;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                //Layout Belirlenince hallediyok.
                vi = inflater.inflate(R.layout.rental_details, null);
                holder = new ViewHolder();

                holder.display_renter = (TextView) vi.findViewById(R.id.txtview_rental_renter);
                holder.display_date = (TextView) vi.findViewById(R.id.txtview_rental_date);
                holder.display_totalPrice = (TextView) vi.findViewById(R.id.txtview_rental_cost);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_renter.setText(_lRental.get(position).getRenterFullName());
            String startDateStr = new SimpleDateFormat("dd/MM/yyyy").format(_lRental.get(position).getStartDate());
            String endDateStr = new SimpleDateFormat("dd/MM/yyyy").format(_lRental.get(position).getEndDate());
            holder.display_date.setText(String.format("%s - %s",startDateStr,endDateStr));
            holder.display_totalPrice.setText(String.valueOf(_lRental.get(position).getTotalPrice()));

        } catch (Exception e) {


        }
        return vi;
    }
}
