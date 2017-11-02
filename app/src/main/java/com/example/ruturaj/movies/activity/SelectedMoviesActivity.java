package com.example.ruturaj.movies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ruturaj.movies.MainActivity.TAG;
import static com.example.ruturaj.movies.MainActivity.api_key;

public class SelectedMoviesActivity extends YouTubeBaseActivity{

    private Movies movie;
    private ImageView backdrop_image;
    private TextView overview;
    private String url = "https://image.tmdb.org/t/p/w500";
    private String video_key;
    private String youtube_api_key = "AIzaSyBIqVjI2BOSZ7GsdfgV1uWeR7KzkZsk-e0";
    private YouTubePlayer.OnInitializedListener onInitializedListener;

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

        Bundle bundleExtras = getIntent().getExtras();
        if(bundleExtras != null){
            movie = (Movies) bundleExtras.getSerializable("SelectedMovie");
            this.setTitle(movie.getTitle());
            overview.setText(movie.getOverview());

            Glide.with(this).load(url + movie.getBackdropPath()).into(backdrop_image);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        final YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        /*
        MovieInterface movieInterface = Client.getClient().create(MovieInterface.class);
        Call<MovieDetails> movieDetailsCall = movieInterface.getSpecificMovieVideo(movie.getId(), api_key);
        movieDetailsCall.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                video_key = response.body().getKey();
                Log.i("KEY ", video_key);
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });*/

        DownloadTask task = new DownloadTask();
        try{
            task.execute("https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=98429bdf1e5e077d0a3727fc98564a5b");
        }
        catch (Exception e) {
            e.printStackTrace();
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

        onInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b)
                    youTubePlayer.cueVideo(video_key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        Button video_play_button = (Button) findViewById(R.id.video_play_button);

        video_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize(youtube_api_key, onInitializedListener);
            }
        });
    }



    public class DownloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection connection;

            try{
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                Log.i("Data ", result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject object = jsonArray.getJSONObject(0);
                video_key = object.getString("key");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(youtube_api_key, onInitializedListener);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
