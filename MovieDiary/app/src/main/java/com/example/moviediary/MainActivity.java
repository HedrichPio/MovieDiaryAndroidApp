package com.example.moviediary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openRegisterMovie(View view) {

        Intent register = new Intent(this,RegisterMovie.class);
        startActivity(register);

    }


    public void openDisplayMovies(View view) {

        Intent displaymovies = new Intent(this,DisplayMovies.class);
        startActivity(displaymovies);

    }

    public void openFavourites(View view) {

        Intent favourites = new Intent(this,Favourites.class);
        startActivity(favourites);

    }


    public void openEditMovies(View view){

        Intent edit = new Intent(this, EditMovies.class);
        startActivity(edit);
    }


    public void openSearch(View view){
        Intent search = new Intent(this, SearchActivity.class);
        startActivity(search);
    }


    public void openRatings(View view){
        Intent rating = new Intent(this, RatingsActivity.class);
        startActivity(rating);
    }




    
}