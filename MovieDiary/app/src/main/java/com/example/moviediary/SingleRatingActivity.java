package com.example.moviediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;


public class SingleRatingActivity extends AppCompatActivity {

    String movie_title;

    TextView movietitle_textview_sra;
    ImageView moviethumbnail_imageview_sra;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_rating);


        Intent intent = getIntent();

        movie_title = intent.getStringExtra(RatingsActivity.EXTRA_MOVIE_TITLE);

        moviethumbnail_imageview_sra = findViewById(R.id.movie_thumbnail_imageview);
        movietitle_textview_sra = findViewById(R.id.single_ratingact_textview);

        getMovieID(movie_title);

    }

    private void getMovieID(String name){

        Thread t1 = new Thread(new Runnable()  {
            @Override
            public void run() {
                extractIDfromJson(NetworkUtils.getMovieIDasJson(name));
            }
        });
        t1.start();


    }

    private void getMovieRating(String ID){

        Thread t2 = new Thread(new Runnable()  {
            @Override
            public void run() {
                extractRatingfromJson(NetworkUtils.getMovieRatingasJson(ID));
            }
        });
        t2.start();

    }

    private void extractIDfromJson(String s){

        String imgurl = null;
        Bitmap imgbitmap = null;


        try {
            JSONObject jsonObject = new JSONObject(s);

            JSONArray jsonArray = jsonObject.getJSONArray("results");

            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

            String id = jsonObject1.getString("id");

            imgurl = jsonObject1.getString("image");


            InputStream is = null;

            is = new URL(imgurl).openStream();

            imgbitmap = BitmapFactory.decodeStream(is);

            updateImageview(imgbitmap);

            extractRatingfromJson(NetworkUtils.getMovieRatingasJson(id));



        }
        catch (Exception e) {

            // update the UI to show failed results.
            updateUI("JSON Retrieval Failed");

            e.printStackTrace();

        }
    }



    private void extractRatingfromJson(String s){

        try {
            JSONObject jsonObject = new JSONObject(s);

            String rating = jsonObject.getString("totalRating");

            updateUI(rating);

        }

        catch (Exception e) {
            // update the UI to show failed results.
            e.printStackTrace();
            updateUI("No Ratings Yet");

        }
    }


    private void updateUI(String rate){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                movietitle_textview_sra.setText("Rating : " + rate);

            }
        });

    }


    private void updateImageview(Bitmap imgbitmp){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                moviethumbnail_imageview_sra.setImageBitmap(imgbitmp);

        } });

    }

}