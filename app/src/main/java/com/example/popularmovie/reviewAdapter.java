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

public class reviewAdapter  extends RecyclerView.Adapter<reviewAdapter.MyViewHolder> {
    private List<Review> reviewList;
    private Context context;

    public reviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new reviewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.MyViewHolder holder, int position) {

        holder.author.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView author, content;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        author=itemView.findViewById(R.id.review_name);
        content=itemView.findViewById(R.id.review_content);

    }
}

}




