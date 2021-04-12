package com.example.moviediary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {

    ListView listvew_r;

    ArrayList<Movie> allmovies_List = new ArrayList<>();
    String[] movietitles_array;
    DatabaseHelper DB = new DatabaseHelper(this);
    String value = "";

    public static final String EXTRA_MOVIE_TITLE = "com.example.editmoviesactivity.extra.REPLY";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        listvew_r = findViewById(R.id.ratings_listview);

        allmovies_List = DB.getAllMovies();

        if (!allmovies_List.isEmpty()) {
            movietitles_array = new String[allmovies_List.size()];

            for(int m=0; m<allmovies_List.size();m++){

                movietitles_array[m]=allmovies_List.get(m).getTitle();

            }


            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, movietitles_array);


            listvew_r.setBackgroundResource(R.drawable.rounded_corner_rectangle);

            listvew_r.setAdapter(adapter);

            listvew_r.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            listvew_r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub

                    value = adapter.getItem(position);


                }
            });
        } else {

            showAlertDialogForEmpty("No Movies Added!", "Please add atleast one Movie to view Ratings");

        }
    }

    public void showMovieRatings(View view){

        if(value.equals("")){
            showAlertDialog("No Items Selected!","Please Select a Movie and try Again.");
        }
        else{

            Intent ratingIntent = new Intent(this,SingleRatingActivity.class);

            ratingIntent.putExtra(EXTRA_MOVIE_TITLE,value);

            startActivity(ratingIntent);
        }

    }

    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }

    public void showAlertDialogForEmpty(String messageType, String message){

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