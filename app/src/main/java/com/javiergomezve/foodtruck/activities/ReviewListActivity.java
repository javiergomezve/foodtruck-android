package com.javiergomezve.foodtruck.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.javiergomezve.foodtruck.R;
import com.javiergomezve.foodtruck.adapter.FoodTruckReviewsAdapter;
import com.javiergomezve.foodtruck.data.DataService;
import com.javiergomezve.foodtruck.model.FoodTruck;
import com.javiergomezve.foodtruck.model.FoodTruckReview;
import com.javiergomezve.foodtruck.view.ItemDecorator;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private FoodTruck truck;
    private ArrayList<FoodTruckReview> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        truck = getIntent().getParcelableExtra(FoodTruckListActivity.EXTRA_ITEM_TRUCK);

        ReviewsDownloaded listener = new ReviewsDownloaded() {
            @Override
            public void success(Boolean success) {
                if (success) {
                    setUpRecycler();
                }
            }
        };

        reviews = DataService.getInstance().getAllTruckReviews(this, truck.getId(), listener);
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_foodtruck_review);
        recyclerView.setHasFixedSize(true);
        FoodTruckReviewsAdapter adapter = new FoodTruckReviewsAdapter(reviews);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(0,0,0, 10));
    }

    public interface ReviewsDownloaded {
        void success(Boolean success);
    }
}
