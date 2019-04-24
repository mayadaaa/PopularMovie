package com.example.popularmovie;

import java.util.List;

public class MovieDetails {

    private Integer page;

    private Integer totalResults;


    private Integer totalPages;

    private List<Movie> results = null;


    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
    @Override
    public String toString() {
        return "SupModel{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }
}


