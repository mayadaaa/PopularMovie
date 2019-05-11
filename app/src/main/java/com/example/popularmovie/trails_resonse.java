package com.example.popularmovie;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class trails_resonse {
    @SerializedName("results")

    List<Trailer>results=new ArrayList<>();
    @SerializedName("id")
    int id ;

    public trails_resonse(List<Trailer> results, int id) {
        this.results = results;
        this.id = id;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public int getId() {
        return id;
    }
}
