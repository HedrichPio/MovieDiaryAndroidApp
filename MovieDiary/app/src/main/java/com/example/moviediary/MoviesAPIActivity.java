package com.example.moviediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MoviesAPIActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    //declare variables for intents
    public static final String EXTRA_MOVIE_ID = "com.example.mratingsactivity.extra.ID";
    public static final String EXTRA_MOVIE_IMG = "com.example.mratingsactivity.extra.IMG";

    //ui components
    ListView apiMoviesListview_m;

    //variables
    String m_title;

    String[] mtitles_array;

    ArrayList<MovieAPI> moviesAPI_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_a_p_i);

        Intent intent = getIntent();

        m_title = intent.getStringExtra(RatingsActivity.EXTRA_MOVIE_TITLE);

        apiMoviesListview_m = findViewById(R.id.apiMoviesListview);

        getMovieIDs(m_title);

    }



//method to get the ids by trigering the network calls
    private void getMovieIDs(String name) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                extractIDsfromJson(NetworkUtils.getMovieIDasJson(name));
            }
        });
        t1.start();


    }


    //method to extract the ids from JSON
    private void extractIDsfromJson(String s) {

        ArrayList<MovieAPI> movieAPIArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);

            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {


                String m_id = jsonArray.getJSONObject(i).getString("id");
                String mtitle = jsonArray.getJSONObject(i).getString("title");
                String m_img = jsonArray.getJSONObject(i).getString("image");

                MovieAPI movieAPI = new MovieAPI(m_id, mtitle, m_img);

                movieAPIArrayList.add(movieAPI);

            }

            updateListView(movieAPIArrayList);



        } catch (Exception e) {

            // update the UI to show failed results.
            //updateUI("JSON Retrieval Failed");

            e.printStackTrace();

        }
    }



//method to populate the listview using movies coming from the API
    private void updateListView(ArrayList<MovieAPI> allmovies_list){

        moviesAPI_list = allmovies_list;

        mtitles_array = new String[allmovies_list.size()];

        for(int m=0; m<allmovies_list.size();m++){

            mtitles_array[m]=allmovies_list.get(m).getTitle();

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mtitles_array);


        runOnUiThread(new Runnable() {
            public void run() {
                apiMoviesListview_m.setAdapter(adapter);
            }
        });


        apiMoviesListview_m.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub

                String value = adapter.getItem(position);

                switchToSingleRatingActivity(position);

            }
        });

    }


    //method to switch to the activity which displays ratings and the image
    private void switchToSingleRatingActivity(int position){

        Intent s_ratingIntent = new Intent(this,SingleRatingActivity.class);

        Log.d(TAG, moviesAPI_list.get(position).getId());
        Log.d(TAG, moviesAPI_list.get(position).getImgUrl());

        s_ratingIntent.putExtra(EXTRA_MOVIE_ID,moviesAPI_list.get(position).getId());
        s_ratingIntent.putExtra(EXTRA_MOVIE_IMG,moviesAPI_list.get(position).getImgUrl());

        startActivity(s_ratingIntent);


    }
}