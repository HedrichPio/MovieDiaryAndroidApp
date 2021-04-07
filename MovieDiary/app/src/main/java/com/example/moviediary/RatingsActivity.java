package com.example.moviediary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        listvew_r = findViewById(R.id.ratings_listview);

        allmovies_List = DB.getAllMovies();

        movietitles_array = new String[allmovies_List.size()];

        for(int m=0; m<allmovies_List.size();m++){

            movietitles_array[m]=allmovies_List.get(m).getTitle();

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, movietitles_array);

        listvew_r.setAdapter(adapter);

        listvew_r.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listvew_r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub

                value = adapter.getItem(position);


            }
        });
    }

    public void showMovieRatings(View view){

        if(value.equals("")){
            showAlertDialog("No Items Selected!","Please Select a Movie and try Again.");
        }
        else{
            showAlertDialog("Selected!",value);

//            Intent editIntent = new Intent(this,EditSingleMovie.class);
//            editIntent.putExtra(EXTRA_MOVIE,movie);
//
//            startActivity(editIntent);
        }

    }

    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }
}