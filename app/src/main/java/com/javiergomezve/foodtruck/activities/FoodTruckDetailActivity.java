package com.javiergomezve.foodtruck.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.model.FoodTruck;

public class FoodTruckDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FoodTruck truck;

    private TextView truckName, truckFoodType, truckAvgCost;
    private Button reviewsButton, addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        truck = getIntent().getParcelableExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK);

        truckName = findViewById(R.id.dt_truck_name);
        truckName.setText(truck.getName());
        truckFoodType = findViewById(R.id.dt_truck_foodType);
        truckFoodType.setText(truck.getFoodType());
        truckAvgCost = findViewById(R.id.dt_truck_avgCost);
        truckAvgCost.setText("$" + Double.toString(truck.getAvgCost()));

        reviewsButton = findViewById(R.id.dt_reviews);
        addReviewButton = findViewById(R.id.dt_add_review);

        // Obtain the SupportMapFragment and get notified when; the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng truckLocation = new LatLng(truck.getLatitude(), truck.getLongitude());
        mMap.addMarker(new MarkerOptions().position(truckLocation).title(truck.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(truckLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(truckLocation, 10));

        setUpMap();
    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
