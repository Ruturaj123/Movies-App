package com.example.ruturaj.movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ruturaj on 27-10-2017.
 */

public interface MovieInterface {
    @GET("movie/top_rated")
    Call<MovieDetails> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Call<MovieDetails> getSpecificMovie(@Path("id") int id, @Query("api_key") String api_key);
}
