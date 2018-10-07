package com.stackroute.movieservice.exceptions;

public class MovieListEmptyExpception extends Exception{

    private String msg;

    public MovieListEmptyExpception() {
    }

    public MovieListEmptyExpception(String msg) {
        super(msg);
        this.msg = msg;
    }
}
