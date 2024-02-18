package com.example.mhike;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.mhike.databinding.ActivityMapBinding;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng yangon = new LatLng(16.8409, -96.1735);
        //16.8409° N, 96.1735° E21.9588° N, 96.0891° E

        //LatLng myanmar = new LatLng(21.9162, 95.9560); 16.8409° N, 96.1735° E
        //Myanmar LatLng 21.9162° N, 95.9560° E

        mMap.addMarker(new MarkerOptions().position(yangon).title("Marker in Yangon"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yangon));

        LatLng mandalay = new LatLng(21.9588, -96.0891 );
        mMap.addMarker(new MarkerOptions().position(mandalay).title("Marker in mandalay"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mandalay));

        mMap.addPolyline(new PolylineOptions()
                .add(mandalay, yangon)
                .width(5)
                .color(Color.RED));

        computeDistance(mandalay, yangon);

    }
    // New location - latitude: 37.421998333333335, longitude: -122.084
    private void computeDistance(LatLng startCoords, LatLng destCoords) {
        double startLatRads = degreesToRadians(startCoords.latitude);
        double startLongRads = degreesToRadians(startCoords.longitude);
        double destLatRads = degreesToRadians(destCoords.latitude);
        double destLongRads = degreesToRadians(destCoords.longitude);

        double radius = 6371; //radius of the Earth in km

        double distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) +
                Math.cos(startLatRads) * Math.cos(destLatRads) *
                        Math.cos(startLongRads - destLongRads))* radius;

        Log.i("XXXXXXXXXX", "The distance is: " + distance);

    }

    private double degreesToRadians(double degrees) {
        double radians = (degrees * Math.PI)/180;
        return radians;
    }

}