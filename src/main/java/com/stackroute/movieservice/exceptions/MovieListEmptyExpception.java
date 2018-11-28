package com.stackroute.movieservice.exceptions;

public class MovieListEmptyExpception extends Exception{



    public MovieListEmptyExpception() {
    }

    public MovieListEmptyExpception(String msg) {
        super(msg);
    }
}
