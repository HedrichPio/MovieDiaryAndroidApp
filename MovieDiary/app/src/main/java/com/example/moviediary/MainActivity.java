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

//method to open registration  activity
    public void openRegisterMovie(View view) {

        Intent register = new Intent(this,RegisterMovie.class);
        startActivity(register);

    }

    //method to open display all movies  activity
    public void openDisplayMovies(View view) {

        Intent displaymovies = new Intent(this,DisplayMovies.class);
        startActivity(displaymovies);

    }

    //method to open activity containing all favourites
    public void openFavourites(View view) {

        Intent favourites = new Intent(this,Favourites.class);
        startActivity(favourites);

    }


    //method to open activity to edit movies
    public void openEditMovies(View view){

        Intent edit = new Intent(this, EditMovies.class);
        startActivity(edit);
    }


    //method to open the actvity with the search function
    public void openSearch(View view){
        Intent search = new Intent(this, SearchActivity.class);
        startActivity(search);
    }


//method to open the activity to show movie ratings
    public void openRatings(View view){
        Intent rating = new Intent(this, RatingsActivity.class);
        startActivity(rating);
    }


    
}