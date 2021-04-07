package com.example.moviediary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    public static final String TABLE_NAME = "all_movies_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "YEAR";
    public static final String COL_4 = "DIRECTOR";
    public static final String COL_5 = "ACTORS";
    public static final String COL_6 = "RATINGS";
    public static final String COL_7 = "REVIEW";
    public static final String COL_8 = "FAVOURITES";

    static final private int DB_VER = 1;

    SQLiteDatabase myDB;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITLE TEXT, " +
                "YEAR INTEGER, " +
                "DIRECTOR TEXT, " +
                "ACTORS TEXT, " +
                "RATINGS INTEGER, " +
                "REVIEW TEXT," +
                "FAVOURITES INTEGER DEFAULT 0)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public Boolean addMovietoDB(Movie movie){

        myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,movie.getTitle());
        contentValues.put(COL_3,movie.getYear());
        contentValues.put(COL_4,movie.getDirector());
        contentValues.put(COL_5,movie.getActors());
        contentValues.put(COL_6,movie.getRating());
        contentValues.put(COL_7,movie.getReview());
        contentValues.put(COL_8,movie.getFavourite());

        long result = myDB.insert(TABLE_NAME,null,contentValues);

        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Movie> getAllMovies(){

        ArrayList<Movie> movieList = new ArrayList<>();

        String selectStm = "SELECT * FROM " + TABLE_NAME;

        myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery(selectStm, null);

        if(cursor.moveToFirst()) {
            do{
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getInt(2));
                movie.setDirector(cursor.getString(3));
                movie.setActors(cursor.getString(4));
                movie.setRating(cursor.getInt(5));
                movie.setReview(cursor.getString(6));

                if(cursor.getInt(7)==1){
                    movie.setFavourite(true);
                }
                else {
                    movie.setFavourite(false);}

                movieList.add(movie);

            }while (cursor.moveToNext());
        }

        movieList.sort(new SortingComparator());
        return movieList;
    }



    public Boolean updateFavourites(String title, int isFavourite){


        myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_8,isFavourite);

        long result = myDB.update(TABLE_NAME, contentValues, "TITLE = ?", new String[]{ title });

        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public Boolean updateMovie(String title, Movie movie){


        myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,movie.getTitle());
        contentValues.put(COL_3,movie.getYear());
        contentValues.put(COL_4,movie.getDirector());
        contentValues.put(COL_5,movie.getActors());
        contentValues.put(COL_6,movie.getRating());
        contentValues.put(COL_7,movie.getReview());
        contentValues.put(COL_8,movie.getFavourite());

        long result = myDB.update(TABLE_NAME, contentValues, "TITLE = ?", new String[]{ title });

        if(result==-1){
            return false;
        }
        else {
            return true;
        }

    }



    public void deleteMovie(String name){

        myDB = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + name + "'";

        myDB.execSQL(query);
    }


}
