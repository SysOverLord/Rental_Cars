package com.example.RentalCars.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.Person;
import com.example.RentalCars.R;

import java.util.ArrayList;

public class AdapterPerson extends ArrayAdapter<Person> {
    private static final String LOG_TAG = "PersonListAdapter";
    private Activity activity;
    private ArrayList<Person> lPerson;
    private static LayoutInflater inflater = null;


    public AdapterPerson(Activity activity,int textViewResourceId,ArrayList<Person> _lPerson){
        super(activity,textViewResourceId,_lPerson);
        try{
            this.activity = activity;
            this.lPerson = _lPerson;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){
            Log.e(LOG_TAG, "Construction error", e);
        }
    }

    public int getCount() {
        return lPerson.size();
    }
    public Person getItem(Person position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_Name;
        public TextView display_email;
        public TextView display_City;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                //Layout Belirlenince hallediyok.
                vi = inflater.inflate(R.layout.user_details, null);
                holder = new ViewHolder();

                holder.display_Name = (TextView) vi.findViewById(R.id.txtview_user_fullname);
                holder.display_email = (TextView) vi.findViewById(R.id.txtview_user_mail);
                holder.display_City = (TextView) vi.findViewById(R.id.txtview_user_City);



                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_Name .setText(String.format("%s %s",lPerson.get(position).getFirstName(),
                    lPerson.get(position).getLastName()));
            holder.display_email.setText(lPerson.get(position).getEmail());
            holder.display_City.setText(lPerson.get(position).getAddress().getCity());

        } catch (Exception e) {


        }
        return vi;
    }
}
