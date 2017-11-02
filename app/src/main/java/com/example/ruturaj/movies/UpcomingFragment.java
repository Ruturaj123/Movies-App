package com.example.ruturaj.movies;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ruturaj.movies.MainActivity.TAG;
import static com.example.ruturaj.movies.MainActivity.api_key;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {


    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.upcoming_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieInterface movieInterface = Client.getClient().create(MovieInterface.class);

        Call<MovieDetails> movieDetailsCall = movieInterface.getUpcomingMovies(api_key);
        movieDetailsCall.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                List<Movies> moviesList = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(moviesList, R.layout.movie_layout, getContext()));
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
        return view;
    }

}
