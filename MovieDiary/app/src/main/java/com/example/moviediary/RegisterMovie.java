package com.example.moviediary;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class RegisterMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText title_edittext_r, director_edittext_r, actors_edittext_r, review_edittext_r;
    Spinner year_spinner_r, ratings_spinner_r;
    Button save_button_r;

    String title,director,actors,review;
    int year, rating;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);


        title_edittext_r = findViewById(R.id.title_edittext);
        year_spinner_r = findViewById(R.id.year_spinner);
        director_edittext_r = findViewById(R.id.director_edittext);
        actors_edittext_r = findViewById(R.id.actors_edittext);
        ratings_spinner_r = findViewById(R.id.rating_spinner);
        review_edittext_r = findViewById(R.id.review_edittext);

        save_button_r = findViewById(R.id.save_button);


        if (year_spinner_r != null) {
            year_spinner_r.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the int array and default spinner layout.
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getYears());

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (year_spinner_r != null) {
            year_spinner_r.setAdapter(adapter);
        }



        if (ratings_spinner_r != null) {
            ratings_spinner_r.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the int array and default spinner layout.
        ArrayAdapter adapterrating = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getRatingsArray());

        // Specify the layout to use when the list of choices appears.
        adapterrating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner.
        if (ratings_spinner_r != null) {
            ratings_spinner_r.setAdapter(adapterrating);
        }


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


    //method returns an arraylist containing all the possible rating values from 0 to 10
    private ArrayList<Integer> getRatingsArray(){
        ArrayList<Integer> ratingsArray = new ArrayList<>();

        for(int i = 0; i<=10; i++){
            ratingsArray.add(i);
        }
        return ratingsArray;
    }


    public void showAlertDialog(String messageType, String message){

        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterMovie.this);
        alert.setTitle(messageType);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveMovie(View view) {

        if(save_button_r.getText().equals("Save")){

            title = title_edittext_r.getText().toString();
            year = (int) year_spinner_r.getSelectedItem();
            director = director_edittext_r.getText().toString();
            actors = actors_edittext_r.getText().toString();
            rating = (int) ratings_spinner_r.getSelectedItem();
            review = review_edittext_r.getText().toString();

            if(title.equals("")){
                showAlertDialog("Title","Please provide a Title for the Movie.");
            }
            else if(director.equals("")){
                showAlertDialog("Director","Please provide a Director name.");
            }
            else if(actors.equals("")){
                showAlertDialog("Actors","Atleast one Actor name is required.");
            }
            else if(actors.contains(".")||actors.contains("/")||actors.contains("-")||actors.contains("_")){
                showAlertDialog("Actors","Input in Actors filed contains Invalid Characters.\nNames should be Separated using commas ( , )");
            }
            else if(review.equals("")){
                showAlertDialog("Review","Please fill in the review field.");
            }

            else{

                boolean found = createMovieObject(title,year,director,actors,rating,review);

                if(!found){

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("Title : "+title + "\n");
                    buffer.append("Year : "+year + "\n");
                    buffer.append("Director : "+director+ "\n");
                    buffer.append("Actors : "+actors + "\n");
                    buffer.append("Rating : "+rating + "\n");
                    buffer.append("Review : "+review);

                    showAlertDialog("Movie Details Saved as :",buffer.toString());
                }
                else{
                    showAlertDialog("Up to Date", "Movie already Exists");
                }

                save_button_r.setText("Reset");

            }
        }
        else if(save_button_r.getText().equals("Reset")){

            title_edittext_r.setText("");
            year_spinner_r.setSelection(0);
            director_edittext_r.setText("");
            actors_edittext_r.setText("");
            ratings_spinner_r.setSelection(0);
            review_edittext_r.setText("");

            save_button_r.setText("Save");

        }


    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private Boolean createMovieObject(String mtitle, int myear, String mdirector, String mactors, int mrating, String mreview){

        //ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(mactors.split(",")));

        Movie movie = new Movie(mtitle,myear,mdirector,mactors,mrating,mreview,false);

        ArrayList<Movie> movieListinDB = new ArrayList<>();
        movieListinDB = db.getAllMovies();

        boolean found = false;
        for(Movie m : movieListinDB){

            if(m.getTitle().equals(movie.getTitle()) && m.getYear()==movie.getYear()){
                found=true;
            }
        }

        if(!found){
            boolean isInserted = db.addMovietoDB(movie);

            if(isInserted){
                showToastMessage("Data Successfully Added");
            }
            else{
                showToastMessage("Failed to add Data");
            }
        }
        return found;
    }


    private void showToastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}