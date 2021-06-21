package com.example.RentalCars;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.RentalCars.Entity.Rental;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    private BottomNavigationView mBottomBar;
    private HomeFragment homeFragment;
    private AdvertFragment advertFragment;
    private ProfileFragment profileFragment;
    private MyCarsFragment myCarsFragment;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mBottomBar = (BottomNavigationView)findViewById(R.id.main_page_bottomNavigationView);
        homeFragment = new HomeFragment();
        advertFragment = new AdvertFragment();
        profileFragment = new ProfileFragment();
        myCarsFragment = new MyCarsFragment();
        String userId = getIntent().getStringExtra("userId");

        bundle = new Bundle();
        bundle.putString("userId",userId);
        homeFragment.setArguments(bundle);
        setFragment(homeFragment);

        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottombar_menu_home:
                        bundle = new Bundle();
                        bundle.putString("userId",userId);
                        homeFragment.setArguments(bundle);
                        setFragment(homeFragment);
                        return true;
                    case R.id.bottombar_menu_add:
                        bundle = new Bundle();
                        bundle.putString("userId",userId);
                        advertFragment.setArguments(bundle);
                        setFragment(advertFragment);
                        return true;
                    case R.id.bottombar_menu_profile:
                        bundle = new Bundle();
                        bundle.putString("userId",userId);
                        profileFragment.setArguments(bundle);
                        setFragment(profileFragment);
                        return true;
                    case R.id.bottombar_meny_mycars:
                        bundle = new Bundle();
                        bundle.putString("userId",userId);
                        myCarsFragment.setArguments(bundle);
                        setFragment(myCarsFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_page_frameLayout, fragment);
        transaction.commit();
    }
}