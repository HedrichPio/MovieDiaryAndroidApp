package com.example.moviediary;

import java.util.Comparator;

public class SortingComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {


        int compare = o1.getTitle().compareToIgnoreCase(o2.getTitle());

        if (compare < 0){
            return -1;
        }
        else if (compare > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
