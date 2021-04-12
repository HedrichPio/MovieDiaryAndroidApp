package com.example.moviediary;

import java.util.ArrayList;

public class Movie {

    //initialise movie properties
    String title;
    int year;
    String director;
    String actors;
    int rating;
    String review;
    Boolean isFavourite;

    //constructor
    public Movie() { }

    //constructor with properties
    public Movie(String title, int year, String director, String actors, int rating, String review, Boolean isFavourite) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.actors = actors;
        this.rating = rating;
        this.review = review;
        this.isFavourite = isFavourite;
    }


    //declare getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
