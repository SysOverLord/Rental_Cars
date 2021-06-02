package com.example.RentalCars;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    private BottomNavigationView mBottomBar;
    private HomeFragment homeFragment;
    private AdvertFragment advertFragment;
    private ProfileFragment profileFragment;
    private MyCarsFragment myCarsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mBottomBar = (BottomNavigationView)findViewById(R.id.main_page_bottomNavigationView);
        homeFragment = new HomeFragment();
        advertFragment = new AdvertFragment();
        profileFragment = new ProfileFragment();
        myCarsFragment = new MyCarsFragment();

        setFragment(homeFragment);

        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.bottombar_menu_home:
                        setFragment(homeFragment);
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottombar_menu_add:
                        setFragment(advertFragment);
                        Toast.makeText(getApplicationContext(), "Advertise", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottombar_menu_profile:
                        setFragment(profileFragment);
                        Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottombar_meny_mycars:
                        setFragment(myCarsFragment);
                        Toast.makeText(getApplicationContext(), "My Cars", Toast.LENGTH_SHORT).show();
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