package com.example.moviediary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;

public class EditMovies extends AppCompatActivity {

//initialise the UI components
    ListView editmovies_listview_e;

    //initialise a database instance
    DatabaseHelper DB = new DatabaseHelper(this);

    //initialise an arraylist to store movie objects
    ArrayList<Movie> movieList = new ArrayList<>();


    public static final String EXTRA_MOVIE = "com.example.twoactivities.extra.REPLY";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);



        editmovies_listview_e = findViewById(R.id.editmovies_listview);

        movieList = DB.getAllMovies();

        if (!movieList.isEmpty()) {
            EditMovieAdapter adapter = new EditMovieAdapter(this, movieList);

            // Attach the adapter to a ListView
            editmovies_listview_e.setAdapter(adapter);


            editmovies_listview_e.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub

                    Movie value = adapter.getItem(position);
                    Toast.makeText(getApplicationContext(),value.getTitle(),Toast.LENGTH_SHORT).show();

                    showMoviestoEdit(value.getTitle());

                }
            });
        } else {
            showAlertDialog("No Movies Saved!","Please Register atleast on Movie to enable edit Mode");

        }


    }


    //method to show alert dialogs when required
    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        alert.show();

    }



//method to open new activity to edit a single movie
    public void showMoviestoEdit(String movie){

        Intent editIntent = new Intent(this,EditSingleMovie.class);
        editIntent.putExtra(EXTRA_MOVIE,movie);

        startActivity(editIntent);
        
    }


}