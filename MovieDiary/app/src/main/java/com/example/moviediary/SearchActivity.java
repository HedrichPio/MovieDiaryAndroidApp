package com.example.moviediary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //declare ui Compnonents
    EditText search_edittext_s;
    ListView search_listview_s;
    Button search_button_s;

    //database instance
    DatabaseHelper DB = new DatabaseHelper(this);

    //variables to store movies
    ArrayList<Movie> movieList = new ArrayList<>();
    ArrayList<Movie> selectedMoviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_edittext_s = findViewById(R.id.search_edittext);
        search_listview_s = findViewById(R.id.search_listview);
        search_button_s =  findViewById(R.id.search_lookup_button);



    }


//method to search for movies which matches user entry
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void lookupValueinDB(View view) {

        String query = search_edittext_s.getText().toString().toLowerCase();

        if(query.equals("")){

            showAlertDialog("Invalid!", "Please enter a keyword to Search");
            search_listview_s.setVisibility(View.INVISIBLE);
        }
        else{

            loadMoviestoListview(query);

        }

    }


    //method to load query results on the UI
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadMoviestoListview(String query){

        movieList = DB.getAllMovies();

        if (!movieList.isEmpty()) {
            selectedMoviesList.clear();

            for(Movie m : movieList){

                if(m.getTitle().toLowerCase().contains(query)||m.getDirector().toLowerCase().contains(query)|| m.getActors().toLowerCase().contains(query)){
                    selectedMoviesList.add(m);
                }

            }

            if(!selectedMoviesList.isEmpty()){
                search_listview_s.setVisibility(View.VISIBLE);
                EditMovieAdapter adapter = new EditMovieAdapter(this, selectedMoviesList);
                search_listview_s.setAdapter(adapter);

            }
            else{
                search_listview_s.setVisibility(View.INVISIBLE);
                showAlertDialog("Not Found!", "No similar entries found in your Device" );
            }
        } else {
            showAlertDialogforEmpty("No Movies added!", "Please a atleast one movie to view Search results");
        }

        // Attach the adapter to a ListView
    }

//method to display alert dialogs on requirement
    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }

    //method to display alert dialog when no movies found
    public void showAlertDialogforEmpty(String messageType, String message){

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

}