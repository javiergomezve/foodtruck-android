package com.javiergomezve.foodtruck.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.javiergomezve.foodtruck.activities.FoodTruckListActivity;
import com.javiergomezve.foodtruck.activities.ReviewListActivity;
import com.javiergomezve.foodtruck.model.FoodTruck;
import com.javiergomezve.foodtruck.model.FoodTruckReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataService {
    private static final String TAG = DataService.class.getSimpleName();
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
    }

    public ArrayList<FoodTruck> getAllFoodTrucks(
            Context context, final FoodTruckListActivity.TrucksDownloaded listener
    ) {
        String url = Constants.GET_ALL_FT;
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
                                Double avgCost = foodTruck.getDouble("avgCost");

                                JSONObject geometry = foodTruck.getJSONObject("geometry");
                                JSONObject coordinates = geometry.getJSONObject("coordinates");

                                Double latitude = coordinates.getDouble("lat");
                                Double longitude = coordinates.getDouble("long");

                                FoodTruck t = new FoodTruck(id, name, foodType, avgCost, latitude, longitude);
                                foodTruckList.add(t);
                            }

                            Log.i(TAG, "getAllFoodTrucks: success");
                            listener.success(true);
                        } catch (JSONException e){
                            Log.e(TAG, "getAllFoodTrucks: error " + e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "getAllFoodTrucks: error  " + error.getLocalizedMessage());
                    }
                }
        );

        Volley.newRequestQueue(context).add(getTrucks);

        return foodTruckList;
    }

    public ArrayList<FoodTruckReview> getAllTruckReviews(
            Context context, String truckId,
            final ReviewListActivity.ReviewsDownloaded listener
    ) {
        String url = String.format(Constants.GET_ALL_FT_R, truckId) ;
        final ArrayList<FoodTruckReview> reviewsList = new ArrayList<>();

        final JsonArrayRequest getReviews = new JsonArrayRequest(
                Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int x = 0; x < response.length(); x++) {
                                JSONObject review = response.getJSONObject(x);
                                String id = review.getString("_id");
                                String title = review.getString("title");
                                String text = review.getString("text");

                                FoodTruckReview r = new FoodTruckReview(id, title, text);
                                reviewsList.add(r);
                            }

                            Log.i(TAG, "getAllTruckReviews: success");
                            listener.success(true);
                        } catch (JSONException e){
                            Log.e(TAG, "getAllTruckReviews: error " + e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "getAllTruckReviews: error  " + error.getLocalizedMessage());
                    }
                }
        );

        Volley.newRequestQueue(context).add(getReviews);

        return reviewsList;
    }
}
