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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditSingleMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText title_edittext_esm, director_edittext_esm, actors_edittext_esm, review_edittext_esm;
    RatingBar ratings_ratingbar_esm,ratings2_ratingbar_esm;
    Spinner year_spinner_esm, favourite_spinner_esm;

    Button update_button_esm;

    ArrayList<Movie> moviesList = new ArrayList<>();

    String[] fav_array = {"Favourite", "Not Favourite"};

    String message,s_title,s_director,s_actors,s_review;
    int s_year, s_rating;
    Boolean s_fav;

    DatabaseHelper DB = new DatabaseHelper(this);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_movie);

        Intent intent = getIntent();

        message = intent.getStringExtra(EditMovies.EXTRA_MOVIE);

        title_edittext_esm = findViewById(R.id.single_title_edittext);
        year_spinner_esm = findViewById(R.id.single_year_spinner);
        director_edittext_esm = findViewById(R.id.single_director_edittext);
        actors_edittext_esm = findViewById(R.id.single_actors_edittext);
        ratings_ratingbar_esm = findViewById(R.id.single_ratings_ratingbar);
        ratings2_ratingbar_esm = findViewById(R.id.single_ratingsbar_2);
        review_edittext_esm = findViewById(R.id.single_review_edittext);
        favourite_spinner_esm = findViewById(R.id.single_fav_spinner);

        update_button_esm = findViewById(R.id.single_update_button);


        if (year_spinner_esm != null) {
            year_spinner_esm.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the int array and default spinner layout.
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getYears());

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (year_spinner_esm != null) {
            year_spinner_esm.setAdapter(adapter);
        }



        if (favourite_spinner_esm != null) {
            favourite_spinner_esm.setOnItemSelectedListener(this);
        }


        // Create ArrayAdapter using the int array and default spinner layout.
        ArrayAdapter adapter_fav = new ArrayAdapter(this,android.R.layout.simple_spinner_item,fav_array);

        // Specify the layout to use when the list of choices appears.
        adapter_fav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (favourite_spinner_esm != null) {
            favourite_spinner_esm.setAdapter(adapter_fav);
        }

        getMovieDetails(message);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    //method returns an arraylist containing all the year from 1985 to 2021
    private ArrayList<Integer> getYears(){
        ArrayList<Integer> yearsArray = new ArrayList<>();

        for(int i = 2021; i>=1895; i--){
            yearsArray.add(i);
        }
        return yearsArray;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getMovieDetails(String title){

        moviesList = DB.getAllMovies();

        //Movie movie = new Movie();
        for(Movie m: moviesList){

            if(m.getTitle().equals(title)){

                setValuesInUI(m);
                break;

            }
        }

    }

    private void setValuesInUI(Movie m){

        title_edittext_esm.setText(m.getTitle());

        for(int i=0; i <getYears().size();i++){

            if(getYears().get(i)==m.getYear()){

                year_spinner_esm.setSelection(i);
                break;

            }
        }

        director_edittext_esm.setText(m.getDirector());
        actors_edittext_esm.setText(m.getActors());

        if(m.getRating()<=5){
            ratings_ratingbar_esm.setRating(m.getRating());
        }
        else if(m.getRating()>5){
            ratings_ratingbar_esm.setRating(5);
            ratings2_ratingbar_esm.setRating(m.getRating()-5);
        }
        review_edittext_esm.setText(m.getReview());

        if(m.getFavourite()){
            favourite_spinner_esm.setSelection(0);
        }
        else {
            favourite_spinner_esm.setSelection(1);
        }

    }


    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveNewValuestoDB(View view) {


            s_title = title_edittext_esm.getText().toString();
            s_year = (int) year_spinner_esm.getSelectedItem();
            s_director = director_edittext_esm.getText().toString();
            s_actors = actors_edittext_esm.getText().toString();
            s_rating = (int) (ratings2_ratingbar_esm.getRating() +ratings_ratingbar_esm.getRating());
            s_review = review_edittext_esm.getText().toString();

            if(favourite_spinner_esm.getSelectedItem().equals("Favourite")){
                s_fav = true;
            }
            else{
                s_fav =false;
            }

            if(s_title.equals("")){
                showAlertDialog("Title","Please provide a Title for the Movie.");
            }
            else if(s_director.equals("")){
                showAlertDialog("Director","Please provide a Director name.");
            }
            else if(s_actors.equals("")){
                showAlertDialog("Actors","Atleast one Actor name is required.");
            }
            else if(s_actors.contains(".")||s_actors.contains("/")||s_actors.contains("-")||s_actors.contains("_")){
                showAlertDialog("Actors","Input in Actors filed contains Invalid Characters.\nNames should be Separated using commas ( , )");
            }
            else if(s_review.equals("")){
                showAlertDialog("Review","Please fill in the review field.");
            }

            else{

                Movie movie = new Movie(s_title,s_year,s_director,s_actors,s_rating,s_review,s_fav);
                boolean isUpdated = DB.updateMovie(message,movie);

                if(isUpdated){

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("Title : "+s_title + "\n");
                    buffer.append("Year : "+s_year + "\n");
                    buffer.append("Director : "+s_director+ "\n");
                    buffer.append("Actors : "+s_actors + "\n");
                    buffer.append("Rating : "+s_rating + "\n");
                    buffer.append("Review : "+s_review);

                    showAlertDialog("Movie Details Updated as :",buffer.toString());
                }
                else{
                    showAlertDialog("Failed to Update", "Details were not updated, try again");
                }
            }

    }

}