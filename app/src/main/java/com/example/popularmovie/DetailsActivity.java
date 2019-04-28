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


    @BindView(R.id.Image_poster)
    ImageView imageView;
    @BindView(R.id.Rate)
    TextView rate;
    @BindView(R.id.Header_title)
    TextView title;
    @BindView(R.id.Release_date)
    TextView date;
    @BindView(R.id.Description)
    TextView description;
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


        title.setText(getIntent().getExtras().getString("title"));
        description.setText(getIntent().getExtras().getString("overview"));
        date.setText(getIntent().getExtras().getString("release_date"));
        rate.setText(String.valueOf(getIntent().getExtras().getDouble("vote_average")) + "/10");

        Picasso.with(DetailsActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .into(imageView);
    }
}

