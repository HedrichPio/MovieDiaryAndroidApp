package com.example.moviediary;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMovies extends AppCompatActivity {


    ListView movies_listview_am;
    Button savefav;

    DatabaseHelper DB = new DatabaseHelper(this);

    ArrayList<Movie> movieList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        savefav = findViewById(R.id.allmovies_save_button);
        movies_listview_am = findViewById(R.id.displaymovies_listview);

        movieList = DB.getAllMovies();

        if (!movieList.isEmpty()) {
            // Create the adapter to convert the array to views
            MovieAdapter adapter = new MovieAdapter(this, movieList);

            // Attach the adapter to a ListView
            movies_listview_am.setAdapter(adapter);
        } else {
            showAlertDialog("No Movies added", "Please add atleast one Movie to Display");
        }


    }



    public void savetoDB(View view){

        int i=0;

        for(Movie m:movieList){

            if(m.isFavourite){i=1;}
            else{i=0;}

            boolean isSaved = DB.updateFavourites(m.title,i);

        }

        showToastMessage("Successfully Saved");
    }

    private void showToastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


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


}