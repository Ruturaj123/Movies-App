package com.example.ruturaj.movies;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    ArrayList<String> favMovies = new ArrayList<>();
    ArrayAdapter adapter;
    ListView movies_list;
    SQLiteDatabase favDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favDB = this.openOrCreateDatabase("Favorite Movies", MODE_PRIVATE, null);
        favDB.execSQL("CREATE TABLE IF NOT EXISTS fav (name VARCHAR)");

        movies_list = (ListView) findViewById(R.id.movies_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favMovies);
        movies_list.setAdapter(adapter);
        updateList();
    }

    public void updateList(){
        Cursor c = favDB.rawQuery("SELECT * FROM fav", null);
        if(c.moveToFirst())
            favMovies.clear();
        do{
            favMovies.add(c.getString(c.getColumnIndex("name")));
            Log.i("Movie names:", c.getString(c.getColumnIndex("name")));
        }
        while (c.moveToNext());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }
}
