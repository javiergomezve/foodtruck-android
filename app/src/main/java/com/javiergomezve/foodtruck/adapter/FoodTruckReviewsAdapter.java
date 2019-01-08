package com.javiergomezve.foodtruck.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.holder.FoodTruckReviewHolder;
import com.javiergomezve.foodtruck.model.FoodTruckReview;

import java.util.ArrayList;

public class FoodTruckReviewsAdapter extends RecyclerView.Adapter<FoodTruckReviewHolder>{

    private ArrayList<FoodTruckReview> reviews;

    public FoodTruckReviewsAdapter(ArrayList<FoodTruckReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public void onBindViewHolder(FoodTruckReviewHolder holder, int position) {
        final FoodTruckReview review = reviews.get(position);
        holder.updateUI(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public FoodTruckReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewCard = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_foodtruck_review, parent, false
        );
        return new FoodTruckReviewHolder(reviewCard);
    }
}
