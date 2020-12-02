package com.example.mua.mua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mua.R;
import com.example.mua.mua.account.MuaAccountFragment;
import com.example.mua.mua.portofolio.MuaPortofolioFragment;
import com.example.mua.mua.service.MuaServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuMuaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mua);

        loadFragment(new MuaHomeFragment());

        BottomNavigationView BNV = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        BNV.setOnNavigationItemSelectedListener(botnav);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener botnav = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navHome:
                    fragment = new MuaHomeFragment();
                    break;
                case R.id.navBooking:
                    fragment = new MuaServicesFragment();
                    break;
                case R.id.navNotification:
                    fragment = new MuaPortofolioFragment();
                    break;
                case R.id.navAccount:
                    fragment = new MuaAccountFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
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