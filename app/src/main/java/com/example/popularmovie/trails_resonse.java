package com.example.popularmovie;

import java.util.List;

public class trails_resonse {
List<Trailer>results;
int id ;

    public trails_resonse(List<Trailer> results, int id) {
        this.results = results;
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public int getId() {
        return id;
    }
}
