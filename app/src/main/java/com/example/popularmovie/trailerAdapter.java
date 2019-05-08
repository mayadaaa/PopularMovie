package com.example.popularmovie;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class trailerAdapter extends RecyclerView.Adapter<trailerAdapter.MyViewHolder> {
    private List<Trailer> movieVideoList;
    private Context context;

    public trailerAdapter(List<Trailer> movieVideoList, Context context) {
        this.movieVideoList = movieVideoList;
        this.context = context;
    }

    @NonNull
    @Override
    public trailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new trailerAdapter.MyViewHolder(v, context, movieVideoList);
    }

    @Override
    public void onBindViewHolder(@NonNull trailerAdapter.MyViewHolder holder, int position) {
        holder.click.setText(movieVideoList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return movieVideoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView click;
        private Context context;
        private List<Trailer> movieVideoList;

        public MyViewHolder(View itemView, Context con, List<Trailer> movieVideoList) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.trailer_image);
            this.context = con;
            this.click = itemView.findViewById(R.id.clickable_text);
           this. movieVideoList=movieVideoList;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            Trailer clickedDataItem = movieVideoList.get(i);
            String videoId = movieVideoList.get(i).getKey();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoId));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("VIDEO_ID", videoId);
            context.startActivity(intent);

         //   Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();


        }

    }
    }




