package com.example.ruturaj.movies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ruturaj on 27-10-2017.
 */

public class Client {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static Retrofit mInstance = null;

    public static Retrofit getClient(){
        if(mInstance == null){
            mInstance = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
        }
        return mInstance;
    }
}
