package com.example.ruturaj.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ruturaj on 26-10-2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private static List<Movies> movie;
    private int rowLayout;
    private static Context context;
    private String url = "https://image.tmdb.org/t/p/w500";

    public MoviesAdapter(List<Movies> movie, int rowNumber, Context context){
        this.movie = movie;
        this.rowLayout = rowNumber;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTitle.setText(movie.get(position).getTitle());
        holder.mSubtitle.setText(movie.get(position).getReleaseDate());
        holder.mDescription.setText(movie.get(position).getOverview());
        holder.mRating.setText(movie.get(position).getVoteAverage().toString());
        Glide.with(context).load(url + movie.get(position).getPosterPath()).into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        LinearLayout mLayout;
        TextView mTitle, mSubtitle, mDescription, mRating;
        ImageView mPoster;

        public MyViewHolder(View view){
            super(view);
            mLayout = (LinearLayout) view.findViewById(R.id.movies_layout);
            mSubtitle = (TextView) view.findViewById(R.id.movie_subtitle);
            mTitle = (TextView) view.findViewById(R.id.movie_title);
            mDescription = (TextView) view.findViewById(R.id.description_tv);
            mRating = (TextView) view.findViewById(R.id.rating_tv);
            mPoster = (ImageView) view.findViewById(R.id.poster_path_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movies selectedMovie = movie.get(getAdapterPosition());
            Intent intent = new Intent(context, SelectedMoviesActivity.class);
            intent.putExtra("SelectedMovie", (Serializable) selectedMovie);
            context.startActivity(intent);
        }
    }

}


