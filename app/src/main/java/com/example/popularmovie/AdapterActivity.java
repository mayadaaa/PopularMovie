package com.example.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.MyViewHolder> {

    private List<Movie> mainModels;
    private Context context;

    public AdapterActivity(List<Movie> mainModels, Context context) {
        this.mainModels = mainModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item, parent, false);
        return new MyViewHolder(v, context, mainModels);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + mainModels.get(position). getPosterUri())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailsActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("poster_path", mainModels.get(position). getPosterUri());
                intent.putExtra("title", mainModels.get(position).getTitle());
                intent.putExtra("overview", mainModels.get(position).getOverview());
                intent.putExtra("release_date", mainModels.get(position).getReleaseDate());
                intent.putExtra("vote_average", mainModels.get(position).getVoteAverage());

                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private Context context;
        private List<Movie> Result;

        public MyViewHolder(View itemView, Context con, List<Movie> Models) {
            super(itemView);
            this.context = con;
            this.Result = Models;
            this.imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

           int position = getAdapterPosition();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("title", mainModels.get(position).getTitle());
            intent.putExtra("overview", mainModels.get(position).getOverview());
            intent.putExtra("release_date", mainModels.get(position).getReleaseDate());
            intent.putExtra("vote_average", mainModels.get(position).getVoteAverage());
            intent.putExtra("poster_path", mainModels.get(position). getPosterUri());
            context.startActivity(intent);

        }
    }

}


