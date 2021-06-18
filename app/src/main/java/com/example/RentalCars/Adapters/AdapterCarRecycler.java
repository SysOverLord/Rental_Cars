package com.example.RentalCars.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.R;

import java.util.ArrayList;



public class AdapterCarRecycler extends RecyclerView.Adapter<AdapterCarRecycler.MyViewHolder> {

    ArrayList<Car> mCarList;
    LayoutInflater inflater;

    public AdapterCarRecycler(Context context, ArrayList<Car> carList) {
        this.inflater = LayoutInflater.from(context);
        this.mCarList = carList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.car_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Car selectedCar = mCarList.get(position);
        holder.setData(selectedCar, position);

    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public void setNewList(ArrayList<Car> carList){
        this.mCarList = carList;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView carBrand, carModel,carColor,carPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            carBrand = (TextView) itemView.findViewById(R.id.car_item_txtViewCarBrand);
            carModel = (TextView) itemView.findViewById(R.id.car_item_txtViewCarModel);
            carColor = (TextView) itemView.findViewById(R.id.car_item_txtViewCarColor);
            carPrice = (TextView) itemView.findViewById(R.id.car_item_txtViewCarPrice);

        }

        public void setData(Car selectedCar, int position) {

            this.carBrand.setText(selectedCar.getBrand());
            this.carModel.setText(selectedCar.getModel());
            this.carColor.setText(selectedCar.getColor());
            this.carPrice.setText(String.valueOf(selectedCar.getDailyPrice()));


        }


        @Override
        public void onClick(View v) {

        }


    }
}