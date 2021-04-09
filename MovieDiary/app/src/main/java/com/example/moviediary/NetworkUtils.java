package com.example.moviediary;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {


    //private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String TAG = "MyActivity";


    private static String getRatingInfo(String url){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;


        try {

            URL requestURL = new URL(url);

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }

            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            JSONString = builder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(TAG, JSONString);
        return JSONString;
    }


    static String getMovieIDasJson(String movie_title){

        String url = "https://imdb-api.com/en/API/SearchTitle/k_b859c54x/"+ movie_title;

        return getRatingInfo(url);

    }


    static String getMovieRatingasJson(String movie_ID){

        String url =  "https://imdb-api.com/en/API/UserRatings/k_b859c54x/" + movie_ID;

        return getRatingInfo(url);
    }
}
