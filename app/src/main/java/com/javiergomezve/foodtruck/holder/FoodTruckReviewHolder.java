package com.javiergomezve.foodtruck.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.model.FoodTruckReview;

public class FoodTruckReviewHolder extends RecyclerView.ViewHolder {

    private TextView title, text;

    public FoodTruckReviewHolder(View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.foodtruck_review_title);
        this.text = itemView.findViewById(R.id.foodtruck_review_text);
    }

    public void updateUI(FoodTruckReview review) {
        title.setText(review.getTitle());
        text.setText(review.getText());
    }
}
