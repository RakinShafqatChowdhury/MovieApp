package com.paperfly.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.AccessibilityService;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.paperfly.movieapp.Adapter.AdapterMovie;
import com.paperfly.movieapp.Model.MovieList;
import com.paperfly.movieapp.Model.Search;
import com.paperfly.movieapp.Model.SearchedMovies;
import com.paperfly.movieapp.Network.ApiClient;
import com.paperfly.movieapp.Network.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    MovieApi movieApi;
    private TextView searchSize;
    List<Search> searchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterMovie adapterMovie;
    private ProgressBar bar;
    private String searchtitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        searchSize = findViewById(R.id.resultSize);
        searchSize.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieApi = ApiClient.getMovies();
        bar = new ProgressBar(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String movietitle) {
                bar.setVisibility(View.VISIBLE);

                 if(searchtitle.equals(movietitle)){
                     return false;
                 }else{
                     showMovieList(movietitle);
                     searchtitle = movietitle;
                 }



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchList.clear();
                searchSize.setVisibility(View.GONE);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void showMovieList(String movietitle) {

        Call<SearchedMovies> call = movieApi.getSearchedMovies(movietitle, ApiClient.API_kEY);

        call.enqueue(new Callback<SearchedMovies>() {
            @Override
            public void onResponse(Call<SearchedMovies> call, Response<SearchedMovies> response) {
                searchSize.setVisibility(View.VISIBLE);
                if(response.body().getResponse().contains("True")){
                    searchList = response.body().getSearch();

                    searchSize.setText("Search Result: "+searchList.size());


                }else if(response.body().getResponse().contains("False")){
                    searchSize.setText("Movie not Found");
                }

                adapterMovie = new AdapterMovie(getApplicationContext(), searchList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapterMovie);
                //Log.e("search", "onResponse: "+searchList.size());
            }

            @Override
            public void onFailure(Call<SearchedMovies> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                searchSize.setVisibility(View.GONE);
            }

        });
//        call.enqueue(new Callback<MovieList>() {
//            @Override
//            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
//                if (response.isSuccessful()) {
//
//                    movieLists.add(response.body());
//                }
//
//                adapterMovie = new AdapterMovie(getApplicationContext(), movieLists);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setAdapter(adapterMovie);
//                //adapterMovie.notifyDataSetChanged();
//                bar.setVisibility(View.INVISIBLE);
//                //Log.e("movie", "onResponse: " + movieLists.get(0).getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<MovieList> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                Log.e("Error", "onFailure: " + t.getMessage());
//                bar.setVisibility(View.INVISIBLE);
//            }
//        });
    }
}