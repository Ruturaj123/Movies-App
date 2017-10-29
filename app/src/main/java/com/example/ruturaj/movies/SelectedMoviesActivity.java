package com.example.ruturaj.movies;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SelectedMoviesActivity extends AppCompatActivity {

    private Movies movie;
    private ImageView backdrop_image;
    private TextView overview;
    private String url = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_movies);

        backdrop_image = (ImageView) findViewById(R.id.backdrop_iv);
        overview = (TextView) findViewById(R.id.overview_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab_favorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fab_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        });

        Bundle bundleExtras = getIntent().getExtras();
        if(bundleExtras != null){
            movie = (Movies) bundleExtras.getSerializable("SelectedMovie");
            this.setTitle(movie.getTitle());
            overview.setText(movie.getOverview());

            Glide.with(this).load(url + movie.getBackdropPath()).into(backdrop_image);
        }

    }

}
