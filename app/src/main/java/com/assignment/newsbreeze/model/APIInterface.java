package com.assignment.newsbreeze.model;

import com.assignment.newsbreeze.model.data.DataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIInterface {
    //GET https://newsapi.org/v2/top-headlines?country=in&apiKey=API_KEY
    @GET("/v2/top-headlines")
    Call<DataModel> getBreakingNewsItems(@Query("country") String country, @Header("Authorization") String authHeader);

    @GET("/v2/everything")
    Call<DataModel> getSearchNewsItems(@Query("q") String search, @Header("Authorization") String authHeader);

}
