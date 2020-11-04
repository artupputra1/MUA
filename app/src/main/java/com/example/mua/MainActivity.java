package com.example.mua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mua.account.AccountFragment;
import com.example.mua.booking.BookingFragment;
import com.example.mua.homepage.HomeFragment;
import com.example.mua.notification.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView BNV = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        BNV.setOnNavigationItemSelectedListener(botnav);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener botnav = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navHome:
                    fragment = new HomeFragment();
                    break;
                case R.id.navBooking:
                    fragment = new BookingFragment();
                    break;
                case R.id.navNotification:
                    fragment = new NotificationFragment();
                    break;
                case R.id.navAccount:
                    fragment = new AccountFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContent, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}