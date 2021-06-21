package com.example.RentalCars.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.Person;
import com.example.RentalCars.R;
import com.example.RentalCars.ShowUsersActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        public ImageButton display_Delete_User;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                //Layout Belirlenince hallediyoruz.
                vi = inflater.inflate(R.layout.user_details, null);
                holder = new ViewHolder();

                holder.display_Name = (TextView) vi.findViewById(R.id.txtview_user_fullname);
                holder.display_email = (TextView) vi.findViewById(R.id.txtview_user_mail);
                holder.display_City = (TextView) vi.findViewById(R.id.txtview_user_City);
                holder.display_Delete_User = (ImageButton) vi.findViewById(R.id.btn_delete_user);

                vi.setTag(holder);
            }
            else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_Name .setText(String.format("%s %s",lPerson.get(position).getFirstName(),
                    lPerson.get(position).getLastName()));
            holder.display_email.setText(lPerson.get(position).getEmail());
            holder.display_City.setText(lPerson.get(position).getAddress().getCity());
            holder.display_Delete_User.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUserAndRelativeInformation(lPerson.get(position).getUserId());
                }
            });
        }
        catch (Exception e) {

        }
        return vi;
    }

    private void deleteUserAndRelativeInformation(String userIdToDelete){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("users").orderByChild("userId").equalTo(userIdToDelete);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DatabaseReference reference = dataSnapshot.getRef();
                    reference.setValue(null);
                }
                query.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query query2 = myRef.child("cars").orderByChild("ownerId").equalTo(userIdToDelete);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DatabaseReference reference = dataSnapshot.getRef();
                    reference.setValue(null);
                }
                query2.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query3 = myRef.child("rentals").orderByChild("renterId").equalTo(userIdToDelete);
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DatabaseReference reference = dataSnapshot.getRef();
                    reference.setValue(null);
                }
                query3.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
