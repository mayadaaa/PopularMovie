package com.example.popularmovie;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    String id ;
    @SerializedName("author")
    String author ;
    @SerializedName("content")
    String content ;
    @SerializedName("url")
    String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }
}
