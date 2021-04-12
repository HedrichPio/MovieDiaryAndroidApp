package com.example.moviediary;

public class MovieAPI {

    //properties for movies in the API
    String id;
    String title;
    String imgUrl;

    //constructor
    public MovieAPI(String id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    //declare getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
