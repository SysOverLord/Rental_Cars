package com.example.RentalCars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.RentalCars.Entity.Car;
import com.example.RentalCars.Entity.CheckingInputs;
import com.example.RentalCars.Entity.DialogHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AdvertFragment extends Fragment {

    private TextInputLayout brand;
    private TextInputLayout model;
    private TextInputLayout color;
    private TextInputLayout price;
    private EditText detail;
    private ImageView imageView;
    private Button btn_add;
    private Button selectImage;

    private String imageId;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 22;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_advert, container, false);
        String userId = getArguments().getString("userId");

        CheckingInputs[] checkingInputs = defineElements(v);
        DialogHelper dialogHelper = DialogHelper.getInstance();
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(checkingInputs, dialogHelper))
                    addCar(v, checkingInputs, userId);
            }
        });
        return v;
    }

    private void addCar(View v, CheckingInputs[] checkingInputs, String userId) {

        String carId = UUID.randomUUID().toString();
        float dailyPrice = Float.parseFloat(checkingInputs[3].getTextInput().getEditText().getText().toString());
        Car newCar = new Car(checkingInputs[0].getTextInput().getEditText().getText().toString().toLowerCase().trim(),
                checkingInputs[1].getTextInput().getEditText().getText().toString().toLowerCase().trim(),
                checkingInputs[2].getTextInput().getEditText().getText().toString().toLowerCase().trim(),
                dailyPrice, checkingInputs[4].getEdText().getText().toString().trim()
                , carId, userId);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cars/" + newCar.getCarId());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myRef.setValue(newCar);
                //BMF STANDS FOR BRAND MODEL FILTER
                myRef.child("BMF").setValue(newCar.getBrand() + "_" + newCar.getModel());

                if(imageId != null){
                    myRef.child("imageId").setValue(imageId);
                }
                else{
                    myRef.child("imageId").setValue("placeholder_500x500.png");
                }

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment HomeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userId",userId);
                HomeFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().
                        replace(((ViewGroup)getView().getParent()).getId(),HomeFragment)
                        .addToBackStack(null).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private CheckingInputs[] defineElements(View v) {
        CheckingInputs[] checkingInputs = {new CheckingInputs("Brand", brand = (TextInputLayout) v.findViewById(R.id.advert_car_brand)),
                new CheckingInputs("Model", model = (TextInputLayout) v.findViewById(R.id.advert_car_model)),
                new CheckingInputs("Color", color = (TextInputLayout) v.findViewById(R.id.advert_car_color)),
                new CheckingInputs("Price", price = (TextInputLayout) v.findViewById(R.id.advert_car_price)),
                new CheckingInputs("Detail", detail = (EditText) v.findViewById(R.id.advert_car_description)),
        };

        btn_add = (Button) v.findViewById(R.id.btn_add);
        selectImage = (Button) v.findViewById(R.id.btn_add_image);
        imageView = (ImageView) v.findViewById(R.id.advert_carImage);

        return checkingInputs;
    }

    private boolean isEmpty(CheckingInputs[] checkingInputs, DialogHelper dialogHelper) {
        for (CheckingInputs input : checkingInputs) {
            if (input.getEdText() != null){
                if (input.getEdText().getText().toString().equals("")) {
                    dialogHelper.ShowMessage("You did not enter the " + input.getName(), getContext());
                    return true;
                }
            } else {
                if (input.getTextInput().getEditText().getText().toString().equals("")) {
                    dialogHelper.ShowMessage("You did not enter the " + input.getName(), getContext());
                    return true;
                }
            }

        }
        return false;
    }


    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image From Here..."),IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  void uploadImage(){
        if(imageUri != null){
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            String uuid = UUID.randomUUID().toString();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference("images/" + uuid);
            storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    imageId = uuid;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT);
                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Uploaded "
                                            + (int)progress + "%");
                        }
                    });
        }
    }
}