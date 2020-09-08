package com.paperfly.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paperfly.movieapp.Adapter.AdapterMovie;
import com.paperfly.movieapp.Model.MovieList;
import com.paperfly.movieapp.Network.ApiClient;
import com.paperfly.movieapp.Network.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPage extends AppCompatActivity {
    MovieApi movieApi;
    private TextView name,year,director,runtime,rating,genre,plot,writers,releaseDate;
    private ImageView moviePoster;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_page);
        String title = getIntent().getStringExtra("movieTitle");
        movieApi = ApiClient.getMovies();
        init();


        Call<MovieList> call = movieApi.getMovies(title, ApiClient.API_kEY);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    Picasso.get().load(response.body().getPoster()).into(moviePoster);
                    name.setText(response.body().getTitle());
                    year.setText(response.body().getYear());
                    director.setText("Director: "+response.body().getDirector());
                    runtime.setText("Runtime: "+response.body().getRuntime());
                    rating.setText(response.body().getImdbRating());
                    genre.setText(response.body().getGenre());
                    plot.setText(response.body().getPlot());
                    releaseDate.setText(response.body().getReleased());
                    writers.setText(response.body().getWriter());

                }

                bar.setVisibility(View.GONE);
                //Log.e("movieDetails", "onResponse: " + response.body().getYear());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(MovieDetailsPage.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: " + t.getMessage());
                bar.setVisibility(View.GONE);
            }

        });

    }

    private void init() {
        name = findViewById(R.id.movieDetailsName);
        year= findViewById(R.id.movieDetailsYear);
        director = findViewById(R.id.movieDetailsDirector);
        runtime = findViewById(R.id.movieDetailsRuntime);
        rating = findViewById(R.id.movieDetailsRating);
        genre = findViewById(R.id.movieGenre);
        plot = findViewById(R.id.movieDetailsPlot);
        moviePoster = findViewById(R.id.movieDetailsPoster);
        writers = findViewById(R.id.movieDetailsWriter);
        bar = findViewById(R.id.progressBar);
        releaseDate = findViewById(R.id.movieDetailesRelease);
        bar.setVisibility(View.VISIBLE);
    }
}