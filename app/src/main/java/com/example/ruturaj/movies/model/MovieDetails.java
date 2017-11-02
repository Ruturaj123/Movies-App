package com.example.ruturaj.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ruturaj on 27-10-2017.
 */

public class MovieDetails {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private List<Movies> results;
    @SerializedName("key")
    private String video_key;

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public int getTotalResults(){
        return total_results;
    }

    public void setTotal_results(int total_results){
        this.total_results = total_results;
    }

    public int getTotalPages(){
        return total_pages;
    }

    public void setTotalPages(int total_pages){
        this.total_pages = total_pages;
    }

    public List<Movies> getResults(){
        return results;
    }

    public void setResults(List<Movies> results){
        this.results = results;
    }

    public String getKey(){
        return video_key;
    }

    public void setKey(String video_key){
        this.video_key = video_key;
    }
}
