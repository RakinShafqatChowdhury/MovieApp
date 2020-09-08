package com.paperfly.movieapp.Network;

import com.paperfly.movieapp.Model.MovieList;
import com.paperfly.movieapp.Model.Search;
import com.paperfly.movieapp.Model.SearchedMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//?i=tt3896198 & apikey=ded1ae92
public interface MovieApi {
    @GET("?")
    Call<SearchedMovies> getSearchedMovies(@Query("s") String title, @Query("apiKey") String api_key);
    @GET("?")
    Call<MovieList> getMovies(@Query("t") String title, @Query("apiKey") String api_key);
}
