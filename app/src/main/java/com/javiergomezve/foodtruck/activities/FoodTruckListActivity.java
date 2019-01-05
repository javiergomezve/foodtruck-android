package com.javiergomezve.foodtruck.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.adapter.FoodTruckAdapter;
import com.javiergomezve.foodtruck.model.FoodTruck;
import com.javiergomezve.foodtruck.view.ItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodTruckListActivity extends AppCompatActivity {

    private final String TAG = FoodTruckListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_list);

        String url = "http://10.0.2.2:3005/api/v1/foodtruck";
        final ArrayList<FoodTruck> foodTruckList = new ArrayList<>();

        final JsonArrayRequest getTrucks = new JsonArrayRequest(
                Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray foodTrucks = response;
                            for (int x = 0; x < foodTrucks.length(); x++) {
                                JSONObject foodTruck = foodTrucks.getJSONObject(x);
                                String name = foodTruck.getString("name");
                                String id = foodTruck.getString("_id");
                                String foodType = foodTruck.getString("foodType");
                                Double avgCost = foodTruck.getDouble("avgcost");

                                JSONObject geometry = foodTruck.getJSONObject("geometry");
                                JSONObject coordinates = geometry.getJSONObject("coordinates");

                                Double latitude = coordinates.getDouble("lat");
                                Double longitude = coordinates.getDouble("long");

                                FoodTruck newFoodTruck = new FoodTruck(id, name, foodType, avgCost, latitude, longitude);
                                foodTruckList.add(newFoodTruck);
                            }

                            Log.i(TAG, foodTruckList.get(0).getName());

                            RecyclerView recyclerView = findViewById(R.id.recycler_foodtruck);
                            recyclerView.setHasFixedSize(true);
                            FoodTruckAdapter adapter = new FoodTruckAdapter(foodTruckList);
                            recyclerView.setAdapter(adapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.addItemDecoration(new ItemDecorator(0,0,0, 10));
                        } catch (JSONException e){
                            Log.v("API", "EXC " + e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("API", "Err " + error.getLocalizedMessage());
                    }
                }
        );

        Volley.newRequestQueue(this).add(getTrucks);
    }
}
