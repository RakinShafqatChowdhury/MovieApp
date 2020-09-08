package com.paperfly.movieapp.Network;

public class ApiClient {

    private ApiClient(){
    };

    public static final String BASE_URL = "http://www.omdbapi.com/";
    public static final String API_kEY = "ded1ae92";

    public static MovieApi getMovies(){
        return RetrofitClient.getClient(BASE_URL).create(MovieApi.class);
    }
}
