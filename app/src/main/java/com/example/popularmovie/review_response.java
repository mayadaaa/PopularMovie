package com.example.popularmovie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class review_response {
    @SerializedName("results")
    private List<Review> Reviews;

    public List<Review> getReviews() {
        return Reviews;
    }

    public void setReviews(List<Review> Reviews) {
        this.Reviews = Reviews;
    }
}
