package com.example.moviediary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


public class EditMovieAdapter extends ArrayAdapter<Movie> {



    public EditMovieAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, 0, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_movie_item_layout, parent, false);
        }


        // Lookup view for data population
        TextView movietitle_textview = (TextView) convertView.findViewById(R.id.edit_movietitle_textview);


        // Populate the data into the template view using the data object
        movietitle_textview.setText(movie.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}
