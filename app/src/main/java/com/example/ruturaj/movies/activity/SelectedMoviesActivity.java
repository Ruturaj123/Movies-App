package com.example.ruturaj.movies;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SelectedMoviesActivity extends AppCompatActivity {

    private Movies movie;
    private ImageView backdrop_image;
    private TextView overview;
    private String url = "https://image.tmdb.org/t/p/w500";

    SQLiteDatabase favDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_movies);

        favDB = this.openOrCreateDatabase("Favorite Movies", MODE_PRIVATE, null);
        favDB.execSQL("CREATE TABLE IF NOT EXISTS fav (name VARCHAR)");
        //favDB.execSQL("DELETE FROM fav");

        backdrop_image = (ImageView) findViewById(R.id.backdrop_iv);
        overview = (TextView) findViewById(R.id.overview_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle bundleExtras = getIntent().getExtras();
        if(bundleExtras != null){
            movie = (Movies) bundleExtras.getSerializable("SelectedMovie");
            this.setTitle(movie.getTitle());
            overview.setText(movie.getOverview());

            Glide.with(this).load(url + movie.getBackdropPath()).into(backdrop_image);
        }

        FloatingActionButton fab_favorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fab_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "INSERT INTO fav (name) VALUES (?)";
                SQLiteStatement statement = favDB.compileStatement(sql);
                statement.bindString(1, movie.getTitle());
                statement.execute();

                Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sql = "DELETE FROM fav WHERE name = ?";
                        SQLiteStatement statement1 = favDB.compileStatement(sql);
                        statement1.bindString(1, movie.getTitle());
                        statement1.execute();
                    }
                }).show();


            }
        });
    }




}
