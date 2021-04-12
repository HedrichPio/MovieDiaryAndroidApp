package com.example.moviediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class SingleRatingActivity extends AppCompatActivity {

    //declare variables
    String movie_id,img_url;

    //declare ui components
    TextView movietitle_textview_sra;
    ImageView moviethumbnail_imageview_sra;

    private static final String TAG = "MyActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_rating);


        Intent intent = getIntent();

        movie_id = intent.getStringExtra(MoviesAPIActivity.EXTRA_MOVIE_ID);
        img_url = intent.getStringExtra(MoviesAPIActivity.EXTRA_MOVIE_IMG);

        moviethumbnail_imageview_sra = findViewById(R.id.movie_thumbnail_imageview);
        movietitle_textview_sra = findViewById(R.id.single_ratingact_textview);

        Log.d(TAG, movie_id);
        Log.d(TAG, img_url);


        setImage(img_url);

        getMovieRating(movie_id);

    }

//method to get the rating for given movie id
    private void getMovieRating(String ID){

        Thread t2 = new Thread(new Runnable()  {
            @Override
            public void run() {
                extractRatingfromJson(NetworkUtils.getMovieRatingasJson(ID));
            }
        });
        t2.start();

    }


    //method to get the rating from the json objects
    private void extractRatingfromJson(String s){

        try {
            JSONObject jsonObject = new JSONObject(s);

            String rating = jsonObject.getString("totalRating");

            updateUI(rating);

        }

        catch (Exception e) {
            // update the UI to show failed results.
            e.printStackTrace();

        }
    }


    //method to the get the image from the url
    private void setImage(String url){

        Log.d(TAG, url);

        try {

            Thread t2 = new Thread(new Runnable()  {
                Bitmap imgbitmap = null;
                InputStream is = null;


                @Override
                public void run() {
                    try {
                        is = new URL(url).openStream();
                    }

                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgbitmap = BitmapFactory.decodeStream(is);

                    if (imgbitmap != null) {

                        updateImageView(imgbitmap);

                    }
                    else {
                        // If none are found, update the UI to
                        Log.d(TAG, "bitmap null");

                    }
                }
            });
            t2.start();


        } catch (Exception e) {

            e.printStackTrace();
            Log.d(TAG, e.toString());

        }




    }


//method to update the Textview with the rating extracted
    private void updateUI(String rate){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                movietitle_textview_sra.setText("Rating : " + rate);

            }
        });

    }


    //method to set the image in the imageview
    private void updateImageView(Bitmap bitmap){

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    moviethumbnail_imageview_sra.setImageBitmap(bitmap);

                }
            });
        } catch (Exception e) {
            Log.d(TAG, "bitmap error");
            e.printStackTrace();
        }

    }


}