package com.example.popularmovie;

import java.util.List;

public class review_response {
    List<Review> results;
    int id;

    public review_response(List<Review> results, int id) {
        this.results = results;
        this.id = id;
    }

    public List<Review> getResults() {
        return results;
    }

    public int getId() {
        return id;
    }
}


