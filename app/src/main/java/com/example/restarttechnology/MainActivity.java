package com.example.restarttechnology;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    TextView locationText;
    ActionBar toolbar;
    BottomAppBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLocation
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else{
           LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            String city = hereLocation(location.getLatitude(), location.getLongitude());
            Log.d("Location", city);
            locationText = findViewById(R.id.locationText);
            locationText.setText(city);
        }

        RecyclerView mainRecyclerView = findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mainRecyclerView.setLayoutManager(layoutManager);

        ArrayList<DataModel> data = new ArrayList<>();
        for (int i=0; i<MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.drawableArray[i],
                    MyData.nameArray[i]
            ));
        }

        RecyclerView.Adapter mainAdapter = new MainAdapter(data);
        mainRecyclerView.setAdapter(mainAdapter);

        //bottomToolBar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.bottomToolbar);
//        toolbar.setContentInsetsRelative(0,0);
//        toolbar.addView(
//                LayoutInflater.from(this).inflate(R.layout.bottom_toolbar,null,false),
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        );

        //bottomAppBar
//        bar = findViewById(R.id.bottomAppBar);
//        bar.replaceMenu(R.menu.navigation);
//        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.homeBottomIcon:
//                        item.setIcon(R.drawable.home_bottom_icon);
//                        break;
//
//                    case R.id.searchBottomIcon:
//                        item.setIcon(R.drawable.search_bottom_icon);
//                        break;
//
//                    case R.id.notificationBottomIcon:
//                        item.setIcon(R.drawable.notification_bottom_icon);
//                        break;
//
//                    case R.id.profileBottomIcon:
//                        item.setIcon(R.drawable.profile_bottom_icon);
//                }
//                return false;
//            }
//        });

        //toolbar
        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    //onItemSelected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("onItemSelected", "ItemSelected");
        if(item.isChecked())
            item.setChecked(false);

        switch (item.getItemId()) {
            case R.id.homeBottomIcon:
                item.setChecked(true);
                item.setIcon(R.drawable.home_bottom_icon);
                break;

            case R.id.searchBottomIcon:
                item.setChecked(true);
                item.setIcon(R.drawable.search_bottom_icon);
                break;

            case R.id.notificationBottomIcon:
                item.setChecked(true);
                item.setIcon(R.drawable.notification_bottom_icon);
                break;
                
            case R.id.profileBottomIcon:
                item.setChecked(true);
                item.setIcon(R.drawable.profile_bottom_icon);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                            && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                    } else {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            String city = hereLocation(location.getLatitude(), location.getLongitude());
                            locationText.setText(city);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                        }
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        locationText.setText(city);
                    }
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private String hereLocation(double lat, double lon) {
        String cityName = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if(addresses.size() > 0) {
                for (Address address : addresses) {
                    if(address.getLocality() != null && address.getLocality().length() > 0) {
                        cityName = address.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
}