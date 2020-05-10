package com.example.sp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.sp.fragments.Today;
import com.example.sp.fragments.calendar;
import com.example.sp.fragments.week;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity {

    Fragment fragment;
    String tag;

    final String TAG = "MainActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.navigation_today:
                    fragment = new Today();
                    break;
                case R.id.navigation_week:
                    fragment = new week();
                    break;
                case R.id.navigation_calendar:
                    fragment = new calendar();
                    break;
            }
            startFragment(fragment);
            return true;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_today);
    }

    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }else{
            Log.d(TAG, "startFragment: Fragment is null");
        }
    }

//
//
//    // create an action bar button
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


}

