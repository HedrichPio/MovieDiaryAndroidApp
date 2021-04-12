package com.example.moviediary;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    ListView fav_listview_f;
    Button fav_save_button_f;

    DatabaseHelper DB = new DatabaseHelper(this);

    ArrayList<Movie> favMovieList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        fav_listview_f =  findViewById(R.id.fav_listview);
        fav_save_button_f = findViewById(R.id.fav_save_button);

        showFavs();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showFavs() {


        for(Movie m : DB.getAllMovies()){

            if(m.getFavourite()){
                favMovieList.add(m);
            }

        }


        if (!favMovieList.isEmpty()) {
            MovieAdapter adapter = new MovieAdapter(this, favMovieList);

            // Attach the adapter to a ListView
            fav_listview_f.setAdapter(adapter);
        } else {
            showAlertDialog("No Favourites", "Favourites will be displayed once Movies are Selected");
        }

    }

    public void saveFavstoDB(View view){

        int i=0;

        for(Movie m:favMovieList){

            if(m.getFavourite()){i=1;}
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