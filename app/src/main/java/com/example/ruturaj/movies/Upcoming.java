package com.example.ruturaj.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upcoming extends AppCompatActivity {

    private static String TAG = Upcoming.class.getSimpleName();
    private final static String api_key = "98429bdf1e5e077d0a3727fc98564a5b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upcoming_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieInterface movieInterface = Client.getClient().create(MovieInterface.class);

        Call<MovieDetails> movieDetailsCall = movieInterface.getUpcomingMovies(api_key);
        movieDetailsCall.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                List<Movies> moviesList = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(moviesList, R.layout.movie_layout, getApplicationContext()));
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
