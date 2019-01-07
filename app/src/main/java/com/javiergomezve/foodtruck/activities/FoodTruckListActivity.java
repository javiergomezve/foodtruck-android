package com.javiergomezve.foodtruck.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.adapter.FoodTruckAdapter;
import com.javiergomezve.foodtruck.data.DataService;
import com.javiergomezve.foodtruck.model.FoodTruck;
import com.javiergomezve.foodtruck.view.ItemDecorator;

import java.util.ArrayList;

public class FoodTruckListActivity extends AppCompatActivity {

    private ArrayList<FoodTruck> trucks = new ArrayList<>();
    public static final String EXTRA_ITEM_TRUCK = "TRUCK";
    private static FoodTruckListActivity foodTruckListActivity;

    public static FoodTruckListActivity getFoodTruckListActivity() {
        return foodTruckListActivity;
    }

    public static void setFoodTruckListActivity(FoodTruckListActivity foodTruckListActivity) {
        FoodTruckListActivity.foodTruckListActivity = foodTruckListActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_list);

        foodTruckListActivity.setFoodTruckListActivity(this);

        TrucksDownloaded listener = new TrucksDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

        trucks = DataService.getInstance().getAllFoodTrucks(this, listener);
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_foodtruck);
        recyclerView.setHasFixedSize(true);
        FoodTruckAdapter adapter = new FoodTruckAdapter(trucks);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0,0,0, 10));
    }

    public void loadFoodTruckDetailActivity(FoodTruck truck) {
        Intent i = new Intent(FoodTruckListActivity.this, FoodTruckDetailActivity.class);
        i.putExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK, truck);
        startActivity(i);
    }

    public interface TrucksDownloaded {
        void success(Boolean success);
    }
}
