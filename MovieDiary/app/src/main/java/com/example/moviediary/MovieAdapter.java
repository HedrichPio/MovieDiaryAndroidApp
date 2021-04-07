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

public class MovieAdapter extends ArrayAdapter<Movie> {


        public MovieAdapter(Context context, ArrayList<Movie> movieList) {
            super(context, 0, movieList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            Movie movie = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_layout, parent, false);
            }

            // Lookup view for data population
            TextView movietitle_textview = (TextView) convertView.findViewById(R.id.movie_title_textview);
            CheckBox fav_checkbox = (CheckBox) convertView.findViewById(R.id.chk);
            fav_checkbox.setTag(position);

            fav_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = (Integer) view.getTag();
                    // Access the row position here to get the correct data item
                    Movie movie1 = getItem(position);
                    // Do what you want here...
                    if(fav_checkbox.isChecked()){
                        movie1.setFavourite(true);

                    }
                    else{
                        movie1.setFavourite(false);
                    }
                }
            });

            // Populate the data into the template view using the data object
            movietitle_textview.setText(movie.getTitle());
            fav_checkbox.setChecked(movie.getFavourite());

            // Return the completed view to render on screen
            return convertView;
        }


}

