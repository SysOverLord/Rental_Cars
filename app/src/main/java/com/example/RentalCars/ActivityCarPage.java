package com.example.RentalCars;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.DialogHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityCarPage extends AppCompatActivity {
    boolean isRentable;
    boolean dbReturned;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras().getString("pageType").equals("myCarPage")){

            setContentView(R.layout.my_car_details);
            imageView = findViewById(R.id.car_details_carImage);
            Bundle extras = getIntent().getExtras();
            Car car = (Car) extras.get("car");
            getImageIdFromDB(car.getCarId());
            createCarPageInformation(car);
            ImageButton imageButton = findViewById(R.id.btn_delete_car);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCar(car.getCarId());
                }
            });
        }
        else{
            DialogHelper dialogHelper = DialogHelper.getInstance();
            setContentView(R.layout.advertisement_car_details);
            imageView = findViewById(R.id.car_details_carImage);
            Bundle extras = getIntent().getExtras();
            String userId = extras.getString("userId");
            Car car = (Car) extras.get("car");
            getImageIdFromDB(car.getCarId());
            createCarPageInformation(car);
            Button button = findViewById(R.id.btn_rent_car);
            if(!car.getOwnerId().equals(userId)){
                Intent rentIntent = new Intent(this,RentActivity.class);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rentIntent.putExtra("renterId",userId);
                        rentIntent.putExtra("rentedCarId",car.getCarId());
                        rentIntent.putExtra("dailyPrice",car.getDailyPrice());
                        rentIntent.putExtra("carOwnerId",car.getOwnerId());
                        startActivity(rentIntent);
                    }
                });
            }
            else
                dialogHelper.ShowMessage("You can't rent your own car", this);

            Button rentHistoryButton = findViewById(R.id.btn_history);
            Intent rentHistoryIntent = new Intent(this,ShowPreviousRentalsActivity.class);
            rentHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rentHistoryIntent.putExtra("carId",car.getCarId());
                    startActivity(rentHistoryIntent);
                }
            });
        }
    }

    public void getImageFromStorage(String imageId){

        if(imageId != null){
            final long FIVE_MEGABYTE = 5 * 1024 * 1024;

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("images/"+imageId);

            storageReference.getBytes(FIVE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);

                    imageView.setMinimumHeight(dm.heightPixels);
                    imageView.setMinimumWidth(dm.widthPixels);
                    imageView.setImageBitmap(bm);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivityCarPage.this,"Encountered error while getting image",Toast.LENGTH_SHORT);
                }
            });
        }
    }

    public void getImageIdFromDB(String carId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("cars/" + carId).child("imageId");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageId = snapshot.getValue(String.class);
                getImageFromStorage(imageId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void deleteCar(String carId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + carId );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.setValue(null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void findOwnerName(TextView textOwner,String ownerId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + ownerId );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ownerFirstName = snapshot.child("firstName").getValue(String.class);
                String ownerLastName = snapshot.child("lastName").getValue(String.class);
                textOwner.setText(String.format("%s %s", ownerFirstName, ownerLastName));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createCarPageInformation(Car car){
        TextView textBrand = findViewById(R.id.txtview_brand);
        TextView textModel = findViewById(R.id.txtview_model);
        TextView textColor = findViewById(R.id.txtview_color);
        TextView textPrice = findViewById(R.id.txtview_dailyprice);
        TextView textDesc = findViewById(R.id.txtview_desc);
        TextView textOwner = findViewById(R.id.txtview_owner);
        textBrand.setText(car.getBrand());
        textModel.setText(car.getModel());
        textColor.setText(car.getColor());
        textPrice.setText(String.format("%s", car.getDailyPrice()));
        textDesc.setText(car.getDesc());
        findOwnerName(textOwner,car.getOwnerId());
    }

    public void createCarPageInformationFromDB(String carId){
        TextView textBrand = findViewById(R.id.txtview_brand);
        TextView textModel = findViewById(R.id.txtview_model);
        TextView textColor = findViewById(R.id.txtview_color);
        TextView textPrice = findViewById(R.id.txtview_dailyprice);
        TextView textDesc = findViewById(R.id.txtview_desc);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars" );
        Query query = myRef.orderByChild("carId").equalTo(carId);
        query.addValueEventListener(new ValueEventListener() {
            Car car = new Car();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    car = dataSnapshot.getValue(Car.class);
                }
                //Arabanın bilgi kutucuklarını değiştir
                textBrand.setText(car.getBrand());
                textModel.setText(car.getModel());
                textColor.setText(car.getColor());
                textPrice.setText(String.format("%s", car.getDailyPrice()));
                textDesc.setText(car.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}