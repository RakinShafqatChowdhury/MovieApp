package com.paperfly.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paperfly.movieapp.Model.MovieList;
import com.paperfly.movieapp.Model.Search;
import com.paperfly.movieapp.MovieDetailsPage;
import com.paperfly.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolder> {
    private Context context;
    private List<Search> listMovie;

    public AdapterMovie(Context context, List<Search> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMovie.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movie_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMovie.ViewHolder holder, int position) {
        holder.name.setText(listMovie.get(position).getTitle());
        holder.year.setText(listMovie.get(position).getYear());
        holder.type.setText(listMovie.get(position).getType());

        Picasso.get().load(listMovie.get(position).getPoster()).into(holder.poster);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = listMovie.get(position).getTitle();
                Intent intent = new Intent(context, MovieDetailsPage.class);
                intent.putExtra("movieTitle",title);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        //Log.e("adapter", "onBindViewHolder: "+listMovie.get(position).getDirector() );
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,year,type;
        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.movieName);
            year = itemView.findViewById(R.id.movieYear);
            type = itemView.findViewById(R.id.movieType);
            poster = itemView.findViewById(R.id.moviePoster);
        }
    }
}
