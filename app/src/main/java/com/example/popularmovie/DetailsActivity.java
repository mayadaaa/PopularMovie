package com.example.popularmovie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.image_poster) ImageView imageView;
    @BindView (R.id.Rate) TextView ratee;
    @BindView (R.id.Header_title) TextView titlee;
    @BindView (R.id.Release_date) TextView  date;
    @BindView   (R.id.Description) TextView description;
    private List<Movie> Model;
    APIinterface movieAPI;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_main);

      ButterKnife.bind(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIinterface.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieAPI = retrofit.create(APIinterface.class);
        String poster = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String rate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");

        titlee.setText(title);
        description.setText(overview);
        ratee.setText(rate);
        date.setText(release);

        Picasso.with(DetailsActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" +getIntent().getExtras().getString("poster_path"))
                .into(imageView);
    }
}

